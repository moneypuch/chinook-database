import * as React from "react";
import { List, Datagrid, TextField, EmailField, Edit, SimpleForm, TextInput, Create, Show, SimpleShowLayout } from "react-admin";

export const CustomerList = (props) => (
    <List {...props}>
        <Datagrid rowClick="edit">
            <TextField source="id" />
            <TextField source="firstName" label="First Name" />
            <TextField source="lastName" label="Last Name" />
            <EmailField source="email" label="Email" />
            <TextField source="address" label="Address" />
            <TextField source="city" label="City" />
            <TextField source="company" label="Company" />
            <TextField source="country" label="Country" />
            <TextField source="phone" label="Phone" />
            <TextField source="postalCode" label="Postal Code" />
            <TextField source="state" label="State" />
        </Datagrid>
    </List>
);

export const CustomerEdit = (props) => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput source="id" disabled />
            <TextInput source="firstName" label="First Name" />
            <TextInput source="lastName" label="Last Name" />
            <TextInput source="email" label="Email" />
            <TextInput source="address" label="Address" />
            <TextInput source="city" label="City" />
            <TextInput source="company" label="Company" />
            <TextInput source="country" label="Country" />
            <TextInput source="phone" label="Phone" />
            <TextInput source="postalCode" label="Postal Code" />
            <TextInput source="state" label="State" />
        </SimpleForm>
    </Edit>
);

export const CustomerCreate = (props) => (
    <Create {...props}>
        <SimpleForm>
            <TextInput source="firstName" label="First Name" />
            <TextInput source="lastName" label="Last Name" />
            <TextInput source="email" label="Email" />
            <TextInput source="address" label="Address" />
            <TextInput source="city" label="City" />
            <TextInput source="company" label="Company" />
            <TextInput source="country" label="Country" />
            <TextInput source="phone" label="Phone" />
            <TextInput source="postalCode" label="Postal Code" />
            <TextInput source="state" label="State" />
        </SimpleForm>
    </Create>
);

export const CustomerShow = (props) => (
    <Show {...props}>
        <SimpleShowLayout>
            <TextField source="id" />
            <TextField source="firstName" label="First Name" />
            <TextField source="lastName" label="Last Name" />
            <EmailField source="email" label="Email" />
            <TextField source="address" label="Address" />
            <TextField source="city" label="City" />
            <TextField source="company" label="Company" />
            <TextField source="country" label="Country" />
            <TextField source="phone" label="Phone" />
            <TextField source="postalCode" label="Postal Code" />
            <TextField source="state" label="State" />
        </SimpleShowLayout>
    </Show>
);