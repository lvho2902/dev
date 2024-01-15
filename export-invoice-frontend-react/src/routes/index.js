import Home from '~/pages/Home';
import Employee from '~/pages/Employee';
import Project from '~/pages/Project';
import Profile from '~/pages/Profile';

import { HeaderOnly } from '~/components/Layout';

const publicRoutes = [
    {
        path: '/',
        component: Home,
    },
    {
        path: '/employee',
        component: Employee,
    },
    {
        path: '/project',
        component: Project,
    },
    // {
    //     path: '/profile',
    //     component: Profile,
    //     Layout: null,
    // },
    {
        path: '/profile',
        component: Profile,
        Layout: HeaderOnly,
    },
];

const privateRoutes = [];

export { publicRoutes, privateRoutes };
