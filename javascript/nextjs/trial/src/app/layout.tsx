import './globals.css'

export default function RootLayout({children}: { children: React.ReactNode }) {
    return (
        <html lang="en">
            <body className="min-h-screen flex flex-col items-center justify-center bg-gray-100">{children}</body>
        </html>
    )
}