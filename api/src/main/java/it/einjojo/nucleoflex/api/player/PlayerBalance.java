package it.einjojo.nucleoflex.api.player;


public interface PlayerBalance {
    /**
     * @return the balance of the player.
     */
    double balance();

    /**
     * @param amount the amount to set the balance to.
     * @throws IllegalArgumentException if the amount is negative.
     */
    void setBalance(double amount);

    /**
     * @param amount the amount to add to the balance.
     * @throws IllegalArgumentException if the amount is negative.
     */
    void addBalance(double amount);

    /**
     * @param player the player to remove the balance from.
     * @param amount the amount to remove from the balance.
     * @throws IllegalArgumentException if the amount is negative.
     */
    void removeBalance(String player, double amount);
}
