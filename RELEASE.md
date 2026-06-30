# Release Guide

Use `master` for development. Use tags for SDK releases.

Do not create branches named like versions, for example `v1.0.2`.

## Publish Next Version

1. Update and verify `master`.

```bash
git checkout master
git pull origin master
./gradlew clean spotlessCheck test publish
```

2. Create and push the release tag.

```bash
git tag v1.0.2
git push origin master
git push origin refs/tags/v1.0.2
```

The `Publish SDK` workflow runs when the `v*` tag is pushed.

## Consumer Dependency

```gradle
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/SrunVeng/sdk-central")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation "com.dcc:sdk-central:1.0.2"
}
```

## If You Create The Wrong Branch

Delete the version branch and create a tag instead.

```bash
git push origin --delete v1.0.2
git branch -d v1.0.2
git tag v1.0.2
git push origin refs/tags/v1.0.2
```
