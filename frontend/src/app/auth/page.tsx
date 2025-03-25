import AuthComponent from "./components/AuthComponent";
import {JSX} from "react";

export default async function AuthPage(
    props: {
        searchParams: Promise<{form?: string }>
    }
): Promise<JSX.Element> {
    const searchParams = await props.searchParams;
    return (
        <div>
            <AuthComponent initialForm={searchParams.form}/>
        </div>
    )
}