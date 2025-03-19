import globals from 'globals'
import js from '@eslint/js';
import reactHooks from 'eslint-plugin-react-hooks'
import reactRefresh from 'eslint-plugin-react-refresh'
import typescriptEslintParser from '@typescript-eslint/parser';
import nextPlugin from '@next/eslint-plugin-next';
import typescriptEslintPlugin from '@typescript-eslint/eslint-plugin';

export default [
    {
      files: ['**/*.{js,jsx,ts,tsx}'],
      languageOptions: {
        ecmaVersion: 2020,
        sourceType: "module",
        parser: typescriptEslintParser,
        globals: globals.browser,
      },

      plugins: {
        'react-hooks': reactHooks,
        'react-refresh': reactRefresh,
        '@typescript-eslint': typescriptEslintPlugin,
        '@next/next': nextPlugin,
      },
      rules: {
        ...js.configs.recommended.rules,
        ...nextPlugin.configs['core-web-vitals'].rules,
        ...typescriptEslintPlugin.configs.recommended.rules,
        ...reactHooks.configs.recommended.rules,
        'react-refresh/only-export-components': [
          'warn',
          { allowConstantExport: true },
        ],
        'react/no-unescaped-entities': 'off',
      },
      ignores: ['dist/**']
    },
  {
    files: ['**/*.{ts,tsx}'],
    rules: {
      ...typescriptEslintPlugin.configs.recommended.rules, // Re-apply @typescript-eslint/recommended for TS files
    },
  },
];