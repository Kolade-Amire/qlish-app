import type { NextConfig } from "next";

const nextConfig: NextConfig = {
    output: process.env.BUILD_STANDALONE === "true" ? "standalone" : undefined,
    reactStrictMode: true
};




module.exports = nextConfig

export default nextConfig;
