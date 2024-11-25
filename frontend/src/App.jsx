// src/App.jsx
import React from 'react';
import {Admin, Resource, CustomRoutes, Link} from 'react-admin';
import { Route } from 'react-router-dom';
import { authProvider, dataProvider } from './dataProvider';
import { CustomerCreate, CustomerEdit, CustomerList, CustomerShow } from "./CustomerList";
import CustomLoginPage from "./CustomLoginPage";
import ChangePassword from "./ChangePassword";
import {CustomLayout} from "./CustomLayout.jsx";


const App = () => (
    <Admin dataProvider={dataProvider} authProvider={authProvider} loginPage={CustomLoginPage} layout={CustomLayout}>
        <nav>
            <Link to="/change-password">Change Password</Link>
        </nav>
        <CustomRoutes>
            <Route path="/change-password" element={<ChangePassword/>}/>
        </CustomRoutes>
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