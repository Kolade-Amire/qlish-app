import React from 'react';

const SignupForm: React.FC = () => {
    return (
        <div>
            <h2 className="text-3xl font-bold text-center mb-6">Hey!</h2>
            <p className="text-center text-gray-500 mb-6">Are you ready to become smarter?</p>
            <form className="space-y-4">
                <button className="w-full h-full flex-row bg-transparent border border-[#ABABAB] py-2 rounded flex items-center cursor-pointer justify-center">
                    <img src="https://www.google.com/favicon.ico" alt="Google" className="h-5 mr-2" />
                    Sign up with Google
                </button>
                <div className="w-full h-full flex flex-col text-center text-[#878686] my-4">or</div>
                <input type="email" placeholder="Your email" className="w-full p-2 border border-[#ABABAB] rounded focus:border-2 focus:border-[#994040] focus:outline-none" />
                <input type="text" placeholder="Choose a username" className="w-full h-full flex flex-col p-2 border border-[#ABABAB] focus:border-2 focus:border-[#994040] focus:outline-none rounded" />
                <input type="password" placeholder="Password" className="w-full h-full flex flex-col p-2 border border-[#ABABAB] focus:border-2 focus:border-[#994040] focus:outline-none rounded" />
                <input type="password" placeholder="Confirm password" className="w-full h-full flex flex-col p-2 border border-[#ABABAB] focus:border-2 focus:border-[#994040] focus:outline-none rounded" />
                <button type="submit" className="w-full h-full cursor-pointer flex flex-col bg-[#2C3E50] text-[#FFF0D3] py-2 rounded">
                    Sign up
                </button>
            </form>
        </div>
    );
};

export default SignupForm;