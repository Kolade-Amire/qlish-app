import React from "react";
import "./globals.css";
import { Source_Sans_3 } from 'next/font/google'
import LandingHeader from "./components/header";
import LandingFooter from "./components/footer";



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
