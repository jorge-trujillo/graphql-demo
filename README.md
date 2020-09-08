# Kotlin GraphQL Demo

Some experiments based on: https://auth0.com/blog/building-graphql-apis-with-kotlin-spring-boot-and-mongodb/

## Security
Using Spring Security along with GraphQL. The implementation assumes that Authentication is happening elsewhere, and that the app is getting blessed headers with the group or scope information.

## References
- Errors: https://medium.com/@philippechampion58/understanding-graphql-error-handling-mechanisms-in-spring-boot-604301c9bedb

## Interesting issues:
- Kotlin does not interop well with mixed hierarchies: 
    - https://github.com/graphql-java/graphql-java/issues/1022
    - https://youtrack.jetbrains.com/issue/KT-6653
