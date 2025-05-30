package me.roland882.axenvoysaddon;

import com.artillexstudios.axenvoy.envoy.Envoy;
import com.artillexstudios.axenvoy.envoy.Envoys;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PAPI extends PlaceholderExpansion {
    private final AxEnvoysAddon plugin;
    private final String hourLabel;
    private final String hoursLabel;
    private final String minuteLabel;
    private final String minutesLabel;
    private final String secondLabel;
    private final String secondsLabel;

    public PAPI(AxEnvoysAddon plugin) {
        this.plugin = plugin;
        this.hourLabel = plugin.getConfig().getString("time-format.hour", "h");
        this.hoursLabel = plugin.getConfig().getString("time-format.hours", "h");
        this.minuteLabel = plugin.getConfig().getString("time-format.minute", "m");
        this.minutesLabel = plugin.getConfig().getString("time-format.minutes", "m");
        this.secondLabel = plugin.getConfig().getString("time-format.second", "s");
        this.secondsLabel = plugin.getConfig().getString("time-format.seconds", "s");
    }

    @Override
    public @NotNull String getIdentifier() {
        return "axenvoysa";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Roland882";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        String[] args = params.split("_");
        String type = args[0];
        if (type.equals("envoy")) {
            if (args.length < 2) {
                return "N/A";
            }
            String ename = args[1];
            Envoy envoy = Envoys.getTypes().get(ename);
            if (envoy == null) {
                plugin.getLogger().warning("Couldn't find envoy: " + ename);
                return "N/A";
            }
            if (envoy.isActive()) {
                String timeLeftStr = PlaceholderAPI.setPlaceholders(player, "%axenvoy_timeleft_" + ename + "%");
                String result = parseTimeString(timeLeftStr);
                if ("---".equals(result)) {
                    return "---";
                }
                long timeLeftSeconds = Long.parseLong(result);
                return formatTime(timeLeftSeconds);
            } else {
                String nextStartStr = PlaceholderAPI.setPlaceholders(player, "%axenvoy_nextstart_" + ename + "%");
                String result = parseTimeString(nextStartStr);
                if ("---".equals(result)) {
                    return "---";
                }
                long nextStartSeconds = Long.parseLong(result);
                return formatTime(nextStartSeconds);
            }
        }
        return "N/A";
    }

    public String parseTimeString(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty() || timeStr.equals("---")) {
            return "---";
        }

        Pattern pattern = Pattern.compile("(\\d{1,2}):(\\d{2}):(\\d{2})|(\\d{1,2}):(\\d{2})|(\\d+)");
        Matcher matcher = pattern.matcher(timeStr.trim());
        if (!matcher.matches()) {
            return "---";
        }

        try {
            long hours = 0, minutes = 0, seconds = 0;
            if (matcher.group(1) != null) {
                hours = Long.parseLong(matcher.group(1));
                minutes = Long.parseLong(matcher.group(2));
                seconds = Long.parseLong(matcher.group(3));
            } else if (matcher.group(4) != null) {
                minutes = Long.parseLong(matcher.group(4));
                seconds = Long.parseLong(matcher.group(5));
            } else if (matcher.group(6) != null) {
                seconds = Long.parseLong(matcher.group(6));
            }
            if (hours < 0 || minutes < 0 || minutes >= 60 || seconds < 0 || seconds >= 60) {
                plugin.getLogger().warning("Invalid time format: " + timeStr);
                return "---";
            }
            return String.valueOf((hours * 3600) + (minutes * 60) + seconds);
        } catch (NumberFormatException | NullPointerException e) {
            return "---";
        }
    }

    public String formatTime(long seconds) {
        if (seconds < 0) {
            return "Unknown type";
        }
        long hours = TimeUnit.SECONDS.toHours(seconds);
        long remainingSecondsAfterHours = seconds - TimeUnit.HOURS.toSeconds(hours);
        long minutes = TimeUnit.SECONDS.toMinutes(remainingSecondsAfterHours);
        long remainingSeconds = remainingSecondsAfterHours - TimeUnit.MINUTES.toSeconds(minutes);

        String hourFormat = hours == 1 ? hourLabel : hoursLabel;
        String minuteFormat = minutes == 1 ? minuteLabel : minutesLabel;
        String secondFormat = remainingSeconds == 1 ? secondLabel : secondsLabel;

        if (hours > 0) {
            return String.format("%d%s %d%s %d%s", hours, hourFormat, minutes, minuteFormat, remainingSeconds, secondFormat);
        } else if (minutes > 0) {
            return String.format("%d%s %d%s", minutes, minuteFormat, remainingSeconds, secondFormat);
        } else {
            return String.format("%d%s", remainingSeconds, secondFormat);
        }
    }
}