
/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}",
        "./pages/**/*.{js,ts,jsx,tsx}",
        "./app/**/*.{js,ts,jsx,tsx}",
        "./components/**/*.{js,ts,jsx,tsx}"
    ],
    theme: {
        extend: {
            colors: {
                carton: '#FFF0D3',
                deepBlue: '#2C3E50',
                fairRed: '#FF6B6B',
                stroke: '#ABABAB',
                red: '#EB0101',
                black: '#000000',
                white: '#FFFFFF'
            },
        },

    },
    fontFamily: {
        sans: ['var(--font-source-sans-3)'],
    },
    plugins: [],
}