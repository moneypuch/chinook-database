import React, { useState } from 'react';
import { Login, Notification, useNotify, useLogin, TextInput, required, SimpleForm, Toolbar, SaveButton } from 'react-admin';

const CustomLoginForm = () => {
    const [hint, setHint] = useState('Default password: Jo5hu4!');
    const notify = useNotify();
    const login = useLogin();

    const submit = async ({ username, password }) => {
        try {
            await login({ username, password });
            notify('Login successful', { type: 'success' });
        } catch (error) {
            notify(`Login failed: ${error.message}`, { type: 'warning' });
            setHint('Check your credentials and try again.');
        }
    };

    return (
        <>
            <div>{hint}</div>
            <SimpleForm onSubmit={submit} toolbar={<CustomToolbar />}>
                <TextInput source="username" name="username" label="Username" validate={required()} fullWidth />
                <TextInput source="password" name="password" type="password" label="Password" validate={required()} fullWidth />
            </SimpleForm>
            <Notification />
        </>
    );
};

const CustomToolbar = props => (
    <Toolbar {...props}>
        <SaveButton label="Login"  />
    </Toolbar>
);

const CustomLoginPage = () => (
    <Login>
        <CustomLoginForm />
    </Login>
);

export default CustomLoginPage;