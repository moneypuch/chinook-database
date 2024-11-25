import * as React from 'react';
import { Layout } from 'react-admin';
import MyAppBar from './MyAppBar';

export const CustomLayout = ({ children }) => (
    <Layout appBar={MyAppBar}>
        {children}
    </Layout>
);