import { ReactNode } from 'react';

export const metadata = {
    title: 'Qlish',
    description: 'Generative AI for quiz',
};

const RootLayout = ({ children }: { children: ReactNode }) => {
    return (
        <html lang="en">
        <body>{children}</body>
        </html>
    );
};

export default RootLayout;
