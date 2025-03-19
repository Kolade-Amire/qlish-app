import AuthComponent from "./components/AuthComponent";
import {JSX} from "react";

export default function AuthPage(
    {searchParams}: {
        searchParams: {form?: string }
    }
): JSX.Element {
    return (
        <div>
            <AuthComponent initialForm={searchParams.form}/>
        </div>
    )
}