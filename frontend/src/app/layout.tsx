import React from "react";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import './globals.css';
import { Source_Sans_3 } from 'next/font/google'
import type {Metadata} from "next";
import LandingHeader from "./components/header.tsx";
import LandingFooter from "./components/footer.tsx";


// eslint-disable-next-line react-refresh/only-export-components
export const metadata: Metadata = {
    title: "Qlish",
    description: "A generative-AI based quiz app",
};

const sourceSans3 = Source_Sans_3({
    subsets: ['latin'],
    weight: ['300', '400', '600', '700'],
    variable: '--font-source-sans-3',
    display: 'swap',
})

export default function RootLayout({
                                       children,
                                   }: {
    children: React.ReactNode
}) {
    return (
        <html lang="en" className={`${sourceSans3.variable}`}>
            <body className="font-sans min-h-screen bg-[#FFF0D3]">
                <LandingHeader/>
                <main>{children}</main>
                <LandingFooter/>
            </body>
        </html>
    )
}
