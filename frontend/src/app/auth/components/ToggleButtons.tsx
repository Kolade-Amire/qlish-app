import React from 'react';

interface ToggleButtonsProps {
    isLogin: boolean;
    onToggle: () => void;
}

const ToggleButtons: React.FC<ToggleButtonsProps> = ({ isLogin, onToggle }) => {
    return (
        <div className="flex rounded-full bg-[#CECDCD] w-40 h-7 cursor-pointer">
            <button
                onClick={onToggle}
                className={`flex-1 rounded-full flex items-center justify-center transition-colors duration-100 text-sm font-medium ${
                    isLogin ? 'bg-[#2c3e50] text-[#FFF0D3]' : 'bg-transparent text-[#2c3e50]'
                }`}
            >
                Login
            </button>
            <button
                onClick={onToggle}
                className={`flex-1 rounded-full flex items-center justify-center transition-colors duration-100 text-sm font-medium ${
                    !isLogin ? 'bg-[#2c3e50] text-[#FFF0D3]' : 'bg-transparent text-[#2c3e50]'
                }`}
            >
                Register
            </button>
        </div>
    );
};

export default ToggleButtons;