package com.pitoftheshadowlord.j2dgame.core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.RateLimiter;
import com.pitoftheshadowlord.j2dgame.core.listeners.JGCollideListener;

public class JGObject implements Comparable<JGObject>{

    private static int ID_SEQUENCE = 0;
    public final int id;

    protected final List<JGObject> children = Lists.newArrayList();
    protected JGObject parent = null;
    protected RateLimiter rateLimit = RateLimiter.create(60);

    private final List<JGCollideListener> collideListeners = Lists.newArrayList();

    public int zOrder = 0;
    public boolean visible = true;
    public boolean destroy = false;

    public final Rectangle rect = new Rectangle(0, 0, 0, 0);
    public final Point renderOffset = new Point(0, 0);

    private Set<String> tags = Sets.newHashSet();

    public JGObject() {
        id = ++ID_SEQUENCE;
    }

    public JGObject(int x, int y) {
        this();
        rect.setLocation(x, y);
    }

    public JGObject(int x, int y, int width, int height) {
        this();
        rect.setLocation(x, y);
        rect.setSize(width, height);
    }

    public void runAction(JGAction action) {
        ActionManager.get().runAction(this, action);
    }

    public void cancelAllActions() {
        for (JGAction action: ActionManager.get().getActions(this)) {
            action.cancel();
        }
    }


    public List<JGObject> getChildren() {
        return children;
    }

    public void addChild(JGObject child, int zdiff) {
        child.zOrder+=zdiff;
        children.add(child);
        child.setParent(this);
        SceneManager.get().register(child);
    }

    public void addChild(JGObject child) {
        addChild(child, 0);
    }

    public void removeChild(JGObject child) {
        children.remove(child);
    }

    public void clearChildren() {
        children.clear();
    }

    public void setParent(JGObject object) {
        parent = object;
    }

    public JGObject getParent() {
        return parent;
    }

    public int getX() {
        return rect.x;
    }

    public int getY() {
        return rect.y;
    }

    public int getWidth() {
        return rect.width;
    }

    public int getHeight() {
        return rect.height;
    }

    public void setPosition(int x, int y) {
        rect.setLocation(x, y);
    }

    public void setPosition(Point p) {
        rect.setLocation(p);
    }

    public Point getPosition() {
        return rect.getLocation();
    }

    public void move(Point p) {
        rect.setLocation(rect.x + p.x, rect.y + p.y);
    }

    public void move(int x, int y) {
        rect.setLocation(rect.x + x, rect.y + y);
    }

    public void moveX(int x) {
        rect.setLocation(rect.x + x, rect.y);
    }

    public void moveY(int y) {
        rect.setLocation(rect.x, rect.y + y);
    }

    public Dimension getSize() {
        return rect.getSize();
    }

    public void setSize(int width, int height) {
        rect.setSize(width, height);
    }

    public void setVisible(boolean value) {
        if (!visible && value) {
            visible = true;
            onShow();
        }
        else if (visible && !value) {
            visible = false;
            onHide();
        }
    }

    public void destroy() {
        this.destroy = true;
        this.visible = false;
    }

    public boolean within(Rectangle r) {
        Point p = rect.getLocation();
        return r.contains(p.x - renderOffset.x, p.y - renderOffset.y);
    }

    public int distanceFrom(Rectangle r) {
        Point p = rect.getLocation();
        return (int) Point.distance(p.x, p.y, r.x, r.y);
    }

    public boolean contains(Point point) {
        Rectangle r = new Rectangle(rect.x - renderOffset.x, rect.y - renderOffset.y, rect.width, rect.height);
        return r.contains(point);
    }

    public boolean intersects(Rectangle other) {
        return other.contains(rect.x - renderOffset.x, rect.y - renderOffset.y, rect.width, rect.height);
    }

    public boolean intersects(JGObject other) {
        return this.rect.intersects(other.rect);
    }

    @Override
    public int compareTo(JGObject go) {
        return zOrder - go.zOrder;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public boolean isTagged(String tag) {
        return this.tags.contains(tag);
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public JGObject addCollideListener(JGCollideListener listener) {
        collideListeners.add(listener);
        return this;
    }

    public boolean isCollidable() {
        return !collideListeners.isEmpty();
    }

    public void executeCollideListeners(JGObject other) {
        for (Iterator<JGCollideListener> iter = collideListeners.iterator(); iter.hasNext();) {
            JGCollideListener listener = iter.next();
            if (listener.collide(this, other)) {
                if (listener.isOneShot()) {
                    iter.remove();
                }
            }
        }
    }

    public void render(Graphics g) { }
    public void update() { }
    public void onShow() { }
    public void onHide() { }
    public void onDestroy() { }
}
