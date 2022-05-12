/** @type {import('ts-jest/dist/types').InitialOptionsTsJest} */
module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'node',
  // testResultsProcessor: "jest-sonar-reporter",
  collectCoverage: true,
  coverageReporters: ["lcov"],
  collectCoverageFrom: [
    "./src/**/*.{js,ts}",
    "!<rootDir>/node_modules/"
  ]
};