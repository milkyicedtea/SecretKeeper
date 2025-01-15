[![forthebadge](https://forthebadge.com/images/badges/made-with-kotlin.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/60-percent-of-the-time-works-every-time.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/works-on-my-machine.svg)](https://forthebadge.com) \
[![forthebadge](https://forthebadge.com/images/featured/featured-built-with-love.svg)](https://forthebadge.com)

# SecretKeeper
A plugin for JetBrains IDEs that helps protect sensitive information from being exposed

## Features
- Warns you when opening files containing sensitive data and blurs the content until you confirm
- Prompts for confirmation before committing files with sensitive data, but only for commits made
  via the IDE's built-in Git tool.
- More features coming soon™‼️
  
<details>
  <summary>Screenshots:</summary>
    <img src="https://i.ibb.co/b3MGhx7/image.png" alt="Opening a file"/>
    <img src="https://i.ibb.co/GtF8bq8/image.png" alt="Committing a file"/>
</details>

## Installation
- Download from the JetBrains marketplace! <br>
[![JetBrains Plugin Version](https://img.shields.io/jetbrains/plugin/v/25768)](https://plugins.jetbrains.com/plugin/25768-secretkeeper)

or

- Download `SecretKeeper.zip` from [releases](https://github.com/milkyicedtea/SecretKeeper/releases)

or

- Build from source: <br>
  Clone this repository, and use gradle to build.
    ```shell
    ./gradlew buildPlugin
    ```
  The current recommended gradle version is 8.10

- Follow [this official guide](https://www.jetbrains.com/help/idea/managing-plugins.html#install_plugin_from_disk) to install
  plugins from disk

## Issues & Feedback
If you encounter any issues or have suggestions, please feel free to let me know by opening an [issue](https://github.com/milkyicedtea/SecretKeeper/issues)
