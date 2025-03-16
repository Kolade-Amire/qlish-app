export default {
    content: [
        "./src/app/**/*.{js,ts,jsx,tsx}",
        "./src/components/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            colors: {
                carton: '#FFF0D3',
                deepBlue: '#2C3E50',
                fairRed: '#FF6B6B',
                stroke: '#ABABAB',
                buttonHover: '#6c6b6b',
                textHover: '#878686',
                red: '#EB0101',
                black: '#000000',
                white: '#FFFFFF'
            },
            fontFamily: {
                sans: ["var(--font-source-sans-3)", "sans-serif"],
            },
        },

    },
    plugins: [],
}