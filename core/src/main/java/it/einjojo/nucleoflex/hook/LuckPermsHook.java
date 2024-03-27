package it.einjojo.nucleoflex.hook;

import it.einjojo.nucleoflex.player.handler.PermissionHandler;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class LuckPermsHook implements PermissionHandler {
    private final LuckPerms luckPerms;

    public LuckPermsHook() {
        if (!isAvailable()) {
            throw new IllegalStateException("LuckPerms is not available");
        }
        this.luckPerms = LuckPermsProvider.get();
    }

    public static boolean isAvailable() {
        try {
            Class.forName("net.luckperms.api.LuckPermsProvider");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public CompletableFuture<String> playerPrefix(UUID uuid) {
        return luckPerms.getUserManager().loadUser(uuid).thenApplyAsync(user -> user.getCachedData().getMetaData().getPrefix());
    }


    public CompletableFuture<String> playerSuffix(UUID uuid) {
        return luckPerms.getUserManager().loadUser(uuid).thenApplyAsync(user -> user.getCachedData().getMetaData().getSuffix());
    }


    public CompletableFuture<String> playerPrimaryGroup(UUID uuid) {
        return luckPerms.getUserManager().loadUser(uuid).thenApplyAsync(User::getPrimaryGroup);
    }


    public CompletableFuture<Boolean> inGroup(UUID uuid, String group) {
        return playerPrimaryGroup(uuid).thenApplyAsync(playerPrimaryGroup -> playerPrimaryGroup.equalsIgnoreCase(group));
    }


    public CompletableFuture<List<String>> permissionGroupNames() {
        CompletableFuture<List<String>> futureGroups = new CompletableFuture<>();

        luckPerms.getGroupManager().loadAllGroups().thenRunAsync(() -> {
            List<String> groupNames = new LinkedList<>();
            luckPerms.getGroupManager().getLoadedGroups().forEach(group -> groupNames.add(group.getName()));
            futureGroups.complete(groupNames);
        });

        return futureGroups;
    }


    @Override
    public boolean hasPermission(UUID player, String permission) {
        if (luckPerms.getUserManager().isLoaded(player)) {
            return Objects.requireNonNull(luckPerms.getUserManager().getUser(player)).getCachedData().getPermissionData().checkPermission(permission).asBoolean();
        }
        return luckPerms.getUserManager().loadUser(player).join().getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }

    @Override
    public CompletableFuture<Boolean> hasPermissionAsync(UUID player, String permission) {
        User loaded = luckPerms.getUserManager().getUser(player);
        if (loaded != null) {
            return CompletableFuture.completedFuture(loaded.getCachedData().getPermissionData().checkPermission(permission).asBoolean());
        }
        return luckPerms.getUserManager().loadUser(player).thenApply(user -> user.getCachedData().getPermissionData().checkPermission(permission).asBoolean());
    }


}
