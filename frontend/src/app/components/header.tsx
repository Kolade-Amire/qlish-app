import Image from "next/image";
import Link from "next/link";

export default function LandingHeader(){
    return (
        <nav className="sticky top-0 shadow-sm backdrop-blur-sm border-b-0.5 border-b-stroke/5 w-full">
            <div className="container mx-auto flex items-center justify-between py-2 px-4 md:px-6">
                <div className="flex items-center">
                    <Image src="/logo.svg"
                           alt="Qlish Logo"
                           width={100}
                           height={100}
                           className="w-auto h-11"/>
                </div>
                <div className="hidden md:flex items-center gap-4">
                    <Link href="#" className="text-[#263238] hover:text-[#455a64] transition-colors">
                        Login
                    </Link>
                    <button className="bg-[#37474f] hover:bg-[#263238] text-white">Sign up</button>
                </div>
            </div>

        </nav>
    )
}