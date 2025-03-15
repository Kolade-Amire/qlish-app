import Image from "next/image";
import Link from "next/link";

export default function LandingPage() {
    return (
        <div>
            {/*Hero Section*/}
            <section className="container mx-auto py-12 px-4 md:px-6 md:py-16">
                <div className="flex flex-col lg:flex-row justify-center items-center max-w-5xl mx-auto">
                    {/*Main text*/}
                    <div className="space-y-6">
                        <h1 className="text-4xl md:text-5xl font-bold text-[#2C3E50] leading-tight">
                            Learn and have fun <br/>
                            the <span className="text-[#FF6B6B]">smart</span> way.
                        </h1>
                        <p className="text-[#878686] text-xl">
                            Create custom quizzes instantly by uploading documents, or explore a vast range of quizzes on academic
                            subjects,
                            general knowledge, sports, or pop culture.
                        </p>

                        {/*Action buttons*/}
                        <div className="flex flex-wrap gap-4">
                            <Link href="#" className="flex">
                                <button
                                    className="bg-[#2C3E50] hover:bg-[#6c6b6b] text-[#FFF0D3] text-[15px] w-40 h-10 rounded-sm text-center justify-center flex items-center font-semibold cursor-pointer gap-3">
                                    Get Started
                                    <svg width="14" height="14" viewBox="0 0 16 16"
                                         xmlns="http://www.w3.org/2000/svg">
                                        <path d="M8 0L6.59 1.41L12.17 7H0V9H12.17L6.59 14.59L8 16L16 8L8 0Z"
                                              fill="#FFF0D3"/>
                                    </svg>
                                </button>
                            </Link>

                            <Link href="#" className="flex">
                                <button
                                    className="w-40 h-10 border-1 border-[#878686] rounded-sm bg-[#FFF0D3] text-[#2C3E50] font-semibold hover:text-[#878686] cursor-pointer">
                                    Talk to us
                                </button>
                            </Link>
                        </div>
                    </div>
                    <div className="mt-8 lg:mt-0">
                        <Image
                            src="/hero.svg"
                            alt="Person with quiz app"
                            width={600}
                            height={600}
                            className="w-full max-w-[600px] h-auto lg:max-w-[800px]"
                        />
                    </div>
                </div>
            </section>

            {/* Reasons to Join Section */}
            <section className="container max-w-5xl mx-auto py-12 px-4 md:px-6 md:py-16 ">
                <h2 className="text-2xl md:text-3xl font-bold text-[#2C3E50] mb-8">More Reasons to Join Us</h2>
                <div className="flex flex-col lg:space-x-8 lg:space-y-0 space-y-8 lg:flex-row justify-center items-center max-w-5xl mx-auto">
                    {[
                        {title: "Compete to stay ahead", image: "/placeholder.svg?height=160&width=220"},
                        {title: "Leverage AI to your taste", image: "/placeholder.svg?height=160&width=220"},
                        {title: "Do more with less effort", image: "/placeholder.svg?height=160&width=220"},
                        {title: "Compete to stay ahead", image: "/placeholder.svg?height=160&width=220"},
                    ].map((item, index) => (
                        <div
                            key={index}
                            className="bg-white rounded-lg overflow-hidden shadow-sm hover:shadow-md transition-shadow"
                        >
                            <div className="h-40 bg-[#dbdbdb] relative">
                                <Image src={item.image || "/placeholder.svg"} alt={item.title} fill
                                       className="object-cover"/>
                            </div>
                            <div className="p-4">
                                <h3 className="font-semibold text-[#263238] mb-2">{item.title}</h3>
                                <p className="text-sm text-[#455a64]">
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
                                    incididunt
                                </p>
                            </div>
                        </div>
                    ))}
                </div>
            </section>

            {/* Stats Section */}
            <section className="container mx-auto py-12 px-4 md:px-6">
                <h2 className="text-2xl md:text-3xl font-bold text-[#263238] text-center mb-8">
                    Trusted by 50,000+ Daily Active Test Takers
                </h2>
                <div className="bg-[#000000] text-[#FFF0D3] rounded-lg overflow-hidden max-w-5xl mx-auto">
                    <div className="grid grid-cols-2 md:grid-cols-4">
                        <div className="p-8 text-center border-r border-b md:border-b-0 border-gray-700">
                            <div className="text-4xl md:text-5xl font-bold mb-2">98%</div>
                            <div className="text-sm">Success rate</div>
                        </div>
                        <div className="p-8 text-center border-b md:border-r md:border-b-0 border-gray-700">
                            <div className="text-4xl md:text-5xl font-bold mb-2">1M+</div>
                            <div className="text-sm">Quizzes completed</div>
                        </div>
                        <div className="p-8 text-center border-r border-gray-700">
                            <div className="text-4xl md:text-5xl font-bold mb-2">30+</div>
                            <div className="text-sm">Subject areas</div>
                        </div>
                        <div className="p-8 text-center">
                            <div className="text-4xl md:text-5xl font-bold mb-2">9/10</div>
                            <div className="text-sm">User rating</div>
                        </div>
                    </div>
                </div>
            </section>

            {/* CTA Section */}
            <section className="container mx-auto py-12 px-4 md:px-6 text-center">
                <div className="max-w-md mx-auto">
                    <Image
                        src="/cta_man.svg"
                        alt="Qlish Mascot"
                        width={400}
                        height={400}
                        className="h-auto w-full mb-6"
                    />
                    <h2 className="text-2xl md:text-3xl font-bold text-[#2C3E50] mb-6">Start your first Qlish!</h2>
                    <Link href="#">
                        <button
                            className="bg-[#2C3E50] hover:bg-[#6c6b6b] text-[#FFF0D3] text-[15px] w-28 h-9 rounded-sm text-center justify-center items-center font-semibold cursor-pointer">Let's
                            go!
                        </button>
                    </Link>

                </div>
            </section>
        </div>
    )
}