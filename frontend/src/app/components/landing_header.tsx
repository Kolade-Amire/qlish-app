'use client';

import { useState } from "react";
import Image from "next/image";
import Link from "next/link";

export default function LandingHeader() {
    const [menuOpen, setMenuOpen] = useState(false);

    const toggleMenu = () => {
        setMenuOpen(!menuOpen);
    };

    return (
        <nav id="landing-nav" className="sticky top-0 backdrop-blur-sm border-b border-[#ABABAB]/20 w-full">
            <div className="container mx-auto flex items-center justify-between py-2 px-4 md:px-6">
                <Link href="/">
                    <div id="landing-logo" className="flex items-center ml-2 md:ml-2 lg:ml-10">
                        <Image src="/logo.svg"
                               alt="Qlish Logo"
                               width={100}
                               height={100}
                               className="w-auto h-11" />
                    </div>
                </Link>

                {/* Desktop navigation */}
                <div className="hidden md:flex items-center gap-x-3 mr-10">
                    <Link href="../auth?form=login">
                        <button className="text-[#2C3E50] font-semibold w-28 h-9 text-[15px] hover:text-[#878686] transition-colors cursor-pointer">Log in</button>
                    </Link>
                    <Link href="../auth?form=signup" className="flex">
                        <button className="bg-[#2C3E50] hover:bg-[#6c6b6b] text-[#FFF0D3] text-[15px] w-28 h-9 rounded-sm text-center justify-center flex items-center font-semibold cursor-pointer">Sign up</button>
                    </Link>
                </div>

                {/* Hamburger menu button for mobile */}
                <button
                    className="md:hidden flex flex-col justify-center items-center mr-2 w-10 h-10 focus:outline-none"
                    onClick={toggleMenu}
                    aria-label="Toggle menu"
                >
                    <span className={`block w-6 h-0.5 bg-[#2C3E50] transition-all duration-300 ease-out ${menuOpen ? 'rotate-45 translate-y-1' : 'mb-1.5'}`}></span>
                    <span className={`block w-6 h-0.5 bg-[#2C3E50] transition-all duration-300 ease-out ${menuOpen ? 'opacity-0' : 'mb-1.5'}`}></span>
                    <span className={`block w-6 h-0.5 bg-[#2C3E50] transition-all duration-300 ease-out ${menuOpen ? '-rotate-45 -translate-y-1' : ''}`}></span>
                </button>
            </div>

            {/* Mobile menu dropdown */}
            <div className={`md:hidden overflow-hidden transition-all duration-300 ease-in-out ${menuOpen ? 'max-h-40 py-4' : 'max-h-0'}`}>
                <div className="flex flex-col items-center gap-4 px-4">
                    <Link href="#" className="w-auto">
                        <button className="text-[#2C3E50] font-semibold w-28 h-9 text-[15px] hover:text-[#878686] transition-colors cursor-pointer">Log in</button>
                    </Link>
                    <Link href="#" className="w-auto">
                        <button className="bg-[#2C3E50] hover:bg-[#6c6b6b] text-[#FFF0D3] text-[15px] w-28 h-9 rounded-sm text-center justify-center flex items-center font-semibold cursor-pointer">Sign up</button>
                    </Link>
                </div>
            </div>
        </nav>
    );
}