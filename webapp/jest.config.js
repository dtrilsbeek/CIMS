module.exports = {
    verbose: true,
    moduleFileExtensions: [
        "js",
        "json",
        "vue"
    ],
    moduleDirectories: [
        "node_modules",
        "src"
    ],
    transform: {
        ".*\\.(vue)$": "vue-jest",
        "^.+\\.js$": "<rootDir>/node_modules/babel-jest"
    },
    collectCoverage: true,
    collectCoverageFrom: [
        "src/components/*.{js,vue}",
        "!**/node_modules/**"
    ],
    coverageReporters: [
        "html",
        "text-summary"
    ],
    moduleNameMapper: {
        "^@/(.*)$": "<rootDir>/src/$1"
    },
}