import React from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import Link from "next/link";

// Define validation schema with zod
const schema = z.object({
    email: z.string().email("Please enter a valid email address"),
    password: z.string().min(1, "Password is required"),
});

type LoginFormData = z.infer<typeof schema>;

const LoginForm: React.FC = () => {
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<LoginFormData>({
        resolver: zodResolver(schema),
    });

    const onSubmit = async (data: LoginFormData) => {
        console.log(data)
    };

    return (
        <div className="h-full flex flex-col justify-center items-center">
            <h2 className="text-3xl font-bold text-center mb-4">Hi there!</h2>
            <p className="text-center text-gray-500 mb-6">Welcome back.</p>
            <form className="space-y-4 w-full max-w-sm" onSubmit={handleSubmit(onSubmit)}>
                {/* Email field */}
                <div>
                    <input
                        {...register('email')}
                        type="email"
                        placeholder="Your email"
                        className="w-full p-2 border border-[#ABABAB] focus:border-2 focus:border-[#994040] focus:outline-none rounded"
                    />
                    {errors.email && <p className="text-red-500 text-sm mt-1">{errors.email.message}</p>}
                </div>
                {/* Password field */}
                <div>
                    <input
                        {...register('password')}
                        type="password"
                        placeholder="Password"
                        className="w-full p-2 border border-[#ABABAB] focus:border-2 focus:border-[#994040] focus:outline-none rounded"
                    />
                    {errors.password && <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>}
                </div>
                {/*Forgot password link*/}
                <Link href="#" className="text-sm text-[#2C3E50] underline block text-right">
                    Forgot password?
                </Link>
                {/*Login action button*/}
                <button
                    type="submit"
                    className="w-full text-[#FFF0D3] bg-[#2C3E50] py-2 rounded cursor-pointer"
                >
                    Login
                </button>
                <div className="text-center text-[#878686] my-4">or</div>
                {/*Sign in with Google button*/}
                <button
                    type="button"
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