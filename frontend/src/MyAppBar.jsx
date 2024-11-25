import * as React from 'react';
import {AppBar, Link, TitlePortal} from 'react-admin';
import {Box} from '@mui/material';
import {LockReset as LockIcon} from '@mui/icons-material';

const MyAppBar = () => (
    <AppBar>
        <TitlePortal/>
        <Box display="flex" alignItems="center" justifyContent="flex-end" flexGrow={1} height="100%">
            <Link to="/change-password" style={{textDecoration: 'none', color: 'inherit', display: 'flex', alignItems: 'center', height: '100%'}}>
                <LockIcon/>
            </Link>
        </Box>
    </AppBar>
);

export default MyAppBar;