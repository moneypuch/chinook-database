// src/App.jsx
import React from 'react';
import { Admin, Resource } from 'react-admin';

import { authProvider, dataProvider } from './dataProvider';
import {CustomerCreate, CustomerEdit, CustomerList, CustomerShow} from "./CustomerList.jsx";
import CustomLoginPage from "./CustomLoginPage.jsx";

const App = () => (
    <Admin dataProvider={dataProvider} authProvider={authProvider} loginPage={CustomLoginPage}>
        <div>Authenticate user Robert.King , Use the standard password: Jo5hu4!</div>
        <Resource
            name="customers"
            list={CustomerList}
            edit={CustomerEdit}
            create={CustomerCreate}
            show={CustomerShow}
        />
    </Admin>
);

export default App;