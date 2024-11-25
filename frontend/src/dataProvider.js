import axios from 'axios';
import { fetchUtils } from 'react-admin';
import simpleRestProvider from 'ra-data-simple-rest';
import { jwtDecode } from 'jwt-decode';

const apiUrl = 'http://localhost:8080/api';
let authToken = '';
let refreshToken = '';

// Custom httpClient to add authorization headers
const httpClient = async (url, options = {}) => {
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers,
    };

    if (authToken) {
        const tokenExpiry = jwtDecode(authToken).exp;
        const currentTime = Math.floor(Date.now() / 1000);

        if (tokenExpiry - currentTime < 60 && refreshToken) {
            console.log("Requesting a new token using refresh token");
            const refreshResponse = await axios.post(`${apiUrl}/refresh-token`, { refreshToken });
            const refreshData = refreshResponse.data;
            authToken = refreshData.token;
            if (refreshData.refreshToken) {
                refreshToken = refreshData.refreshToken;
            }
        }

        headers['Authorization'] = `Bearer ${authToken}`;
    }

    return axios({
        url,
        method: options.method || 'get',
        headers,
        data: options.body,
    }).then(response => ({
        headers: response.headers,
        json: response.data,
    }));
};

// Custom dataProvider to format react-admin parameters
const dataProvider = {
    ...simpleRestProvider(apiUrl, httpClient),
    getList: (resource, params) => {
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;
        const query = {
            ...fetchUtils.flattenObject(params.filter),
            range: `[${(page - 1) * perPage},${page * perPage - 1}]`,
            sort: `["${field}","${order}"]`,
        };
        const url = `${apiUrl}/${resource}?${fetchUtils.queryParameters(query)}`;
        return httpClient(url).then(({ headers, json }) => ({
            data: json,
            total: parseInt(headers['content-range'].split('/').pop(), 10),
        }));
    },
};

const authProvider = {
    login: async ({ username, password }) => {
        try {
            const response = await axios.post(`${apiUrl}/authenticate`, { username, password });
            const { token, refreshToken: newRefreshToken } = response.data;
            authToken = token;
            refreshToken = newRefreshToken || '';
        } catch (error) {
            console.log(error);
            if (error.response) {
                const errorMessage = error.response.data || error.response.statusText || 'Login failed.';
                throw new Error(errorMessage);
            }
            throw new Error('Network error');
        }
    },
    logout: () => {
        authToken = '';
        refreshToken = '';
        return Promise.resolve();
    },
    checkError: (error) => {
        const status = error.response ? error.response.status : error.status;
        if (status === 401 || status === 403) {
            authToken = '';
            refreshToken = '';
            localStorage.removeItem('role');
            return Promise.reject();
        }
        return Promise.resolve();
    },
    checkAuth: () => (authToken ? Promise.resolve() : Promise.reject()),
    getPermissions: () => {
        const role = localStorage.getItem('role');
        return role ? Promise.resolve(role) : Promise.reject();
    },
    changePassword: async ({ oldPassword, newPassword }) => {
        try {
            const response = await axios.post(
                `${apiUrl}/change-password`,
                { oldPassword, newPassword },
                { headers: { 'Authorization': `Bearer ${authToken}` } }
            );
            console.log(response);
            return response.data;
        } catch (error) {
            console.log(error);
            if (error.response) {
                const errorMessage = error.response.data || error.response.statusText || 'Password change failed.';
                throw new Error(errorMessage);
            }
            throw new Error('Network error');
        }
    }
};

export { authProvider, dataProvider };