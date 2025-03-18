"use client";

import React, { useState } from 'react';
import ToggleButtons from './ToggleButtons';
import LoginForm from './LoginForm';
import SignupForm from './SignupForm';
import Illustration from './AuthIllustration';
import Image from "next/image";

const AuthForm: React.FC = () => {
    const [isLogin, setIsLogin] = useState(true);

    const toggleForm = () => {
        setIsLogin(!isLogin);
    };

    return (
        <div className="flex items-center justify-center min-h-screen w-full">
            <div className="flex w-full h-screen">
                {/* Form Section */}
                <div className="flex flex-col items-center w-1/2 p-8 shadow-lg max-[768px]:w-full">
                    <div className="w-full max-w-md flex flex-col flex-1">
                        {/* Logo */}
                        <div className="pt-1 mb-10 flex justify-start">
                            <Image
                                src="/logo.svg"
                                alt="Qlish Logo"
                                width={100}
                                height={100}
                                className="w-auto h-11"
                            />
                        </div>
                        {/* Toggle Buttons*/}
                        <div className="flex justify-center mb-8">
                            <ToggleButtons isLogin={isLogin} onToggle={toggleForm} />
                        </div>
                        {/* Form Container */}
                        <div className="relative w-full h-[400px]">
                            <div className={isLogin ? "block" : "hidden"}>
                                <LoginForm />
                            </div>
                            <div className={isLogin ? "hidden" : "block"}>
                                <SignupForm />
                            </div>
                        </div>
                    </div>
                </div>

                {/* Illustration Section */}
                <div className="w-1/2 shadow-lg max-[768px]:hidden">
                    <div className="w-full h-full">
                        <Illustration />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AuthForm;