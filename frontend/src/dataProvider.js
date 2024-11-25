import {fetchUtils} from 'react-admin';
import simpleRestProvider from 'ra-data-simple-rest';
import {jwtDecode} from 'jwt-decode';

// Constants
const apiUrl = 'http://localhost:8080/api';
let authToken = '';
let refreshToken = '';

// Custom httpClient to add authorization headers
const httpClient = async (url, options = {}) => {
    if (!options.headers) {
        options.headers = new Headers({'Content-Type': 'application/json'});
    }
    if (authToken) {
        const tokenExpiry = jwtDecode(authToken).exp;
        const currentTime = Math.floor(Date.now() / 1000);

        if (tokenExpiry - currentTime < 60) {
            console.log("token expired")// Refresh token if expiring in less than 60 seconds
            if (refreshToken) {
                console.log("request a new token using refresh token");
                const refreshResponse = await fetch(`${apiUrl}/refresh-token`, {
                    method: 'POST',
                    headers: new Headers({'Content-Type': 'application/json'}),
                    body: JSON.stringify({refreshToken})
                });
                const refreshData = await refreshResponse.json();
                authToken = refreshData.token;
                if (refreshData.refreshToken) {
                    refreshToken = refreshData.refreshToken;
                }
            } else {
                throw new Error('Session expired');
            }
        }
        options.headers.set('Authorization', `Bearer ${authToken}`);
    }
    return fetchUtils.fetchJson(url, options);
};

// Custom dataProvider to format react-admin parameters
const dataProvider = {
    ...simpleRestProvider(apiUrl, httpClient),
    getList: (resource, params) => {
        const {page, perPage} = params.pagination;
        const {field, order} = params.sort;
        const query = {
            ...fetchUtils.flattenObject(params.filter),
            range: `[${(page - 1) * perPage},${page * perPage - 1}]`,
            sort: `["${field}","${order}"]`
        };
        const url = `${apiUrl}/${resource}?${fetchUtils.queryParameters(query)}`;
        return httpClient(url).then(({headers, json}) => ({
            data: json,
            total: parseInt(headers.get('content-range').split('/').pop(), 10)
        }));
    }
};

const authProvider = {
    login: async ({username, password}) => {
        const request = new Request(`${apiUrl}/authenticate`, {
            method: 'POST',
            body: JSON.stringify({username, password}),
            headers: new Headers({'Content-Type': 'application/json'}),
        });
        const response = await fetch(request);
        if (response.status < 200 || response.status >= 300) {
            throw new Error(response.statusText);
        }
        const {token, refreshToken: newRefreshToken} = await response.json();
        authToken = token;
        refreshToken = newRefreshToken || '';
    },
    logout: () => {
        authToken = '';
        refreshToken = '';
        return Promise.resolve();
    },
    checkError: (error) => {
        const status = error.status;
        if (status === 401 || status === 403) {
            authToken = '';
            refreshToken = '';
            localStorage.removeItem('role');
            return Promise.reject();
        }
        return Promise.resolve();
    },
    checkAuth: () =>
        authToken ? Promise.resolve() : Promise.reject(),
    getPermissions: () => {
        const role = localStorage.getItem('role');
        return role ? Promise.resolve(role) : Promise.reject();
    }
};

export {authProvider, dataProvider};