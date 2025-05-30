# AxEnvoys-Addon

**AxEnvoys-Addon** is a PlaceholderAPI expansion for Minecraft servers using the AxEnvoys plugin. It provides a placeholder to display the time until an envoy event starts (if inactive) or stops (if active), with customizable singular and plural time format labels via `config.yml`.

## Features
- Displays the time until an envoy event starts or stops using `%axenoysa_envoy_<envoy_name>%`.
- Customizable singular and plural time format labels for hours, minutes, and seconds (e.g., "1 hour" vs. "2 hours").
- Planned support for kills and deaths placeholders (currently under development).

## Installation
1. **Dependencies**:
    - [AxEnvoys](https://builtbybit.com/resources/axenvoys-supply-crates-from-the-sky.30408/)
    - [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)
2. Download the plugin `.jar` file from the [Releases](https://github.com/Roland882/AxEnvoys-Addon/releases) section.
3. Place the `.jar` file in your server's `plugins` folder.
4. Restart the server or run `/papi reload`.
5. (Optional) Edit the `config.yml` file to customize the time format labels.

## Configuration
**Important**: In the AxEnvoys plugin, set time-format: 1 in its configuration to ensure compatibility with this addon.
The plugin generates a `config.yml` file in its folder. Default configuration:

```yaml
time-format:
  hour: "h"
  hours: "h"
  minute: "m"
  minutes: "m"
  second: "s"
  seconds: "s"
```

### Customization
Modify the `config.yml` to change the singular and plural labels for hours, minutes, and seconds. Example:

```yaml
time-format:
  hour: " hour"
  hours: " hours"
  minute: " minute"
  minutes: " minutes"
  second: " second"
  seconds: " seconds"
```

This will display times like `1 hour 30 minutes 15 seconds` or `2 hours 30 minutes 15 seconds`.

### Example Display

![img.png](img.png)

## Placeholders
- `%axenoysa_envoy_<envoy_name>%`: Returns the time until the specified envoy starts (if inactive) or stops (if active).

## Example Usage
For an envoy named `main`, use:
```
%axenoysa_envoy_main%
```
This outputs, e.g., `1h 30m 15s` or `2 hours 30 minutes 15 seconds`, based on the configuration.

## Building the Plugin
1. Clone the repository: `git clone https://github.com/Roland882/AxEnvoys-Addon.git`
2. Build using Gradle: `./gradlew clean build`
3. Find the `.jar` file in the `build/libs` directory.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue on [GitHub](https://github.com/Roland882/AxEnvoys-Addon/issues).

## Support
- **Author**: Roland882
- **Issues**: [GitHub Issues](https://github.com/Roland882/AxEnvoys-Addon/issues)
- **Discord**: Coming soon...
