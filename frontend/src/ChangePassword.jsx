// src/ChangePassword.jsx
import React from 'react';
import { SimpleForm, PasswordInput, useNotify, SaveButton, Toolbar } from 'react-admin';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authProvider } from './dataProvider';

const ChangePassword = () => {
    const [loading, setLoading] = useState(false);
    const notify = useNotify();
    const navigate = useNavigate();

    const handleSubmit = async (values) => {
        setLoading(true);
        try {
            await authProvider.changePassword({
                oldPassword: values.oldPassword,
                newPassword: values.newPassword
            });
            notify('Password changed successfully', { type: 'success' });
            navigate('/');
        } catch (error) {
            notify(`Error: ${error.message}`, { type: 'error' });
        } finally {
            setLoading(false);
        }
    };

    return (
        <SimpleForm
            onSubmit={handleSubmit}
            toolbar={<CustomToolbar loading={loading} />}
        >
            <PasswordInput source="oldPassword" label="Old Password" required />
            <PasswordInput source="newPassword" label="New Password" required />
        </SimpleForm>
    );
};

const CustomToolbar = ({ loading }) => (
    <Toolbar>
        <SaveButton saving={loading} />
    </Toolbar>
);

export default ChangePassword;