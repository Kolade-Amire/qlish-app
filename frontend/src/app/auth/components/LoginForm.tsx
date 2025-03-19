import React from 'react';

const LoginForm: React.FC = () => {
    return (
        <div className="h-full flex flex-col justify-center items-center">
            <h2 className="text-3xl font-bold text-center mb-4">Hi there!</h2>
            <p className="text-center text-gray-500 mb-6">Welcome back.</p>
            <form className="space-y-4 w-full max-w-sm">
                <input
                    type="email"
                    placeholder="Your email"
                    className="w-full p-2 border border-[#ABABAB] focus:border-2 focus:border-[#994040] focus:outline-none rounded"
                />
                <input
                    type="password"
                    placeholder="Password"
                    className="w-full p-2 border border-[#ABABAB] focus:border-2 focus:border-[#994040] focus:outline-none rounded"
                />
                <a href="#" className="text-sm text-[#2C3E50] underline block text-right">
                    Forgot password?
                </a>
                <button
                    type="submit"
                    className="w-full text-[#FFF0D3] bg-[#2C3E50] py-2 rounded cursor-pointer"
                >
                    Login
                </button>
                <div className="text-center text-[#878686] my-4">or</div>
                <button
                    className="w-full bg-transparent border border-[#ABABAB] py-2 rounded flex items-center justify-center cursor-pointer"
                >
                    <img src="https://www.google.com/favicon.ico" alt="Google" className="h-5 mr-2" />
                    Sign in with Google
                </button>
            </form>
        </div>
    );
};

export default LoginForm;