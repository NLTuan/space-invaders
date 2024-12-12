package edu.vanier.spaceshooter.models;

/**
 * The Invader is a FiringSprite that also contains properties like health, it
 * also carries a HpBar object that reflects its remaining health. It also
 * has a movement - pause - movement - pause pattern. It happens in intervals.
 * It carries a deltaClock properties that offsets its behavior from other Invaders
 * so that their actions aren't in sync.
 * @author Le Tuan Huy Nguyen
 */
public abstract class Invader extends FiringSprite {

    private double deltaClock; // This value offsets the inner clock of each invader so that their movements are unsynced 
    private double movementCooldown = 1; // When this timer hits, the invader changes its movement
    private boolean movementUpdated = false; // When this is true, the movement just got updated
    private double pauseCooldown = 0.01; // When this timer hits, the invader remains stationary
    private boolean pauseUpdated = true; // When this is true, the invader just got updated to pause

    private int hitpoints;
    private int hpMax;
    
    private HpBar hpBar;
    
    /**
     * Constructor for Invader 
     * @param x the x position of the sprite
     * @param y the y position of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param imagePath the path of the image sprite
     * @param speed the speed of the sprite
     * @param bulletSpeed the speed of the bullet being shot
     */
    public Invader(int x, int y, int width, int height, String imagePath, double speed, double bulletSpeed) {
        super(x, y, width, height, "enemy", imagePath, speed, bulletSpeed);
        hpBar = new HpBar(this);
        
    }

    public abstract void updateMovement();

    /**
     * Update the HpBar depending on if the Invader is hit
     */
    public void updateHP(){
        hpBar.updateBar();
    }
    
    /**
     * Ramp up the speed of the invader depending on the multiplier. Also shrinks
     * the time interval so that the movement is performed quicker, and shorter
     * pause times. Meant to make enemies harder to target as levels go on.
     * @param mult the factor by how much faster the Invader moves 
     */
    public void speedMultiplier(double mult){
        setSpeed(getSpeed() * (1 + mult));
        movementCooldown /= (1 + mult);
        pauseCooldown /= (1 + mult);
    }
    
    /**
     * Shorten the cooldown by a factor. Meant to make levels harder by having
     * a lower firing cooldown
     * @param mult the factor by how much the cooldown decreases
     */
    public void firingCooldownMult(double mult){
        setFiringCooldown(getFiringCooldown() / (1 + mult));
    }
    
    public double getMovementCooldown() {
        return movementCooldown;
    }

    public void setMovementCooldown(double movementCooldown) {
        this.movementCooldown = movementCooldown;
    }

    public double getPauseCooldown() {
        return pauseCooldown;
    }

    public void setPauseCooldown(double pauseCooldown) {
        this.pauseCooldown = pauseCooldown;
    }

    public boolean isMovementUpdated() {
        return movementUpdated;
    }

    public void setMovementUpdated(boolean movementUpdated) {
        this.movementUpdated = movementUpdated;
    }

    public boolean isPauseUpdated() {
        return pauseUpdated;
    }

    public void setPauseUpdated(boolean pauseUpdated) {
        this.pauseUpdated = pauseUpdated;
    }

    public double getDeltaClock() {
        return deltaClock;
    }

    public void setDeltaClock(double deltaClock) {
        this.deltaClock = deltaClock;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getHpMax() {
        return hpMax;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    public HpBar getHpBar() {
        return hpBar;
    }

    public void setHpBar(HpBar hpBar) {
        this.hpBar = hpBar;
    }
    
    
}
