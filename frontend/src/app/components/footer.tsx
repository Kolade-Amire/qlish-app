import Link from "next/link";

export default function LandingFooter(){
    return (
        <div>
            <footer className="container mx-auto py-12 px-4 md:px-6 border-t border-[#dbdbdb]">
                <div className="flex flex-col flex-wrap lg:space-x-50 lg:space-y-0 space-y-20 justify-start lg:flex-row  items-center max-w-5xl mx-auto">
                    <div>
                        <h3 className="font-semibold text-[#263238] mb-4">About</h3>
                        <ul className="space-y-2">
                            <li>
                                <Link href="#" className="text-[#455a64] hover:text-[#263238] transition-colors">
                                    About us
                                </Link>
                            </li>
                            <li>
                                <Link href="#" className="text-[#455a64] hover:text-[#263238] transition-colors">
                                    Contact us
                                </Link>
                            </li>
                        </ul>
                    </div>
                    <div>
                        <h3 className="font-semibold text-[#263238] mb-4">Solutions</h3>
                        <ul className="space-y-2">
                            <li>
                                <Link href="#" className="text-[#455a64] hover:text-[#263238] transition-colors">
                                    How to
                                </Link>
                            </li>
                        </ul>
                    </div>
                    <div>
                        <h3 className="font-semibold text-[#263238] mb-4">Terms and Conditions</h3>
                        <ul className="space-y-2">
                            <li>
                                <Link href="#" className="text-[#455a64] hover:text-[#263238] transition-colors">
                                    Terms of Use
                                </Link>
                            </li>
                            <li>
                                <Link href="#" className="text-[#455a64] hover:text-[#263238] transition-colors">
                                    Privacy Policy
                                </Link>
                            </li>
                            <li>
                                <Link href="#" className="text-[#455a64] hover:text-[#263238] transition-colors">
                                    Cookie Preferences
                                </Link>
                            </li>
                        </ul>
                    </div>
                    <div>
                        <h3 className="font-semibold text-[#263238] mb-4">Follow us</h3>
                        <div className="flex gap-4">
                            <Link href="#" className="text-[#455a64] hover:text-[#263238] transition-colors">
                                <div className="w-8 h-8 rounded-full bg-[#dbdbdb] flex items-center justify-center">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                                         xmlns="http://www.w3.org/2000/svg">
                                        <path
                                            d="M12 2.163c3.204 0 3.584.012 4.85.07 3.252.148 4.771 1.691 4.919 4.919.058 1.265.069 1.645.069 4.849 0 3.205-.012 3.584-.069 4.849-.149 3.225-1.664 4.771-4.919 4.919-1.266.058-1.644.07-4.85.07-3.204 0-3.584-.012-4.849-.07-3.26-.149-4.771-1.699-4.919-4.92-.058-1.265-.07-1.644-.07-4.849 0-3.204.013-3.583.07-4.849.149-3.227 1.664-4.771 4.919-4.919 1.266-.057 1.645-.069 4.849-.069zm0-2.163c-3.259 0-3.667.014-4.947.072-4.358.2-6.78 2.618-6.98 6.98-.059 1.281-.073 1.689-.073 4.948 0 3.259.014 3.668.072 4.948.2 4.358 2.618 6.78 6.98 6.98 1.281.058 1.689.072 4.948.072 3.259 0 3.668-.014 4.948-.072 4.354-.2 6.782-2.618 6.979-6.98.059-1.28.073-1.689.073-4.948 0-3.259-.014-3.667-.072-4.947-.196-4.354-2.617-6.78-6.979-6.98-1.281-.059-1.69-.073-4.949-.073zm0 5.838c-3.403 0-6.162 2.759-6.162 6.162s2.759 6.163 6.162 6.163 6.162-2.759 6.162-6.163c0-3.403-2.759-6.162-6.162-6.162zm0 10.162c-2.209 0-4-1.79-4-4 0-2.209 1.791-4 4-4s4 1.791 4 4c0 2.21-1.791 4-4 4zm6.406-11.845c-.796 0-1.441.645-1.441 1.44s.645 1.44 1.441 1.44c.795 0 1.439-.645 1.439-1.44s-.644-1.44-1.439-1.44z"
                                            fill="#455a64"
                                        />
                                    </svg>
                                </div>
                            </Link>
                            <Link href="#" className="text-[#455a64] hover:text-[#263238] transition-colors">
                                <div className="w-8 h-8 rounded-full bg-[#dbdbdb] flex items-center justify-center">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                                         xmlns="http://www.w3.org/2000/svg">
                                        <path
                                            d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385h-3.047v-3.47h3.047v-2.642c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953h-1.514c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385c5.737-.9 10.125-5.864 10.125-11.854z"
                                            fill="#455a64"
                                        />
                                    </svg>
                                </div>
                            </Link>
                            <Link href="#" className="text-[#455a64] hover:text-[#263238] transition-colors">
                                <div className="w-8 h-8 rounded-full bg-[#dbdbdb] flex items-center justify-center">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                                         xmlns="http://www.w3.org/2000/svg">
                                        <path
                                            d="M24 4.557c-.883.392-1.832.656-2.828.775 1.017-.609 1.798-1.574 2.165-2.724-.951.564-2.005.974-3.127 1.195-.897-.957-2.178-1.555-3.594-1.555-3.179 0-5.515 2.966-4.797 6.045-4.091-.205-7.719-2.165-10.148-5.144-1.29 2.213-.669 5.108 1.523 6.574-.806-.026-1.566-.247-2.229-.616-.054 2.281 1.581 4.415 3.949 4.89-.693.188-1.452.232-2.224.084.626 1.956 2.444 3.379 4.6 3.419-2.07 1.623-4.678 2.348-7.29 2.04 2.179 1.397 4.768 2.212 7.548 2.212 9.142 0 14.307-7.721 13.995-14.646.962-.695 1.797-1.562 2.457-2.549z"
                                            fill="#455a64"
                                        />
                                    </svg>
                                </div>
                            </Link>
                        </div>
                    </div>
                </div>
                <div className="mt-8 pt-8 border-t border-[#dbdbdb] text-center text-sm text-[#455a64]">
                    Copyright ©2025, Qlish. All rights reserved
                </div>
            </footer>
        </div>
    )
}