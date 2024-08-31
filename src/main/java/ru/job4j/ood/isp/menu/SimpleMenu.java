package ru.job4j.ood.isp.menu;

import java.util.*;

public class SimpleMenu implements Menu {

    private final List<MenuItem> rootElements = new ArrayList<>();

    @Override
    public boolean add(String parentName, String childName, ActionDelegate actionDelegate) {
        boolean rsl = false;
        MenuItem child = new SimpleMenuItem(childName, actionDelegate);
        Optional<ItemInfo> optionalParent = findItem(parentName);
        Optional<ItemInfo> optionalChild = findItem(childName);
        if (optionalParent.isEmpty() && optionalChild.isEmpty()) {
            rootElements.add(child);
            rsl = true;
        } else if (optionalParent.isPresent() && optionalChild.isEmpty()) {
            optionalParent.get().menuItem.getChildren().add(child);
            rsl = true;
        }
        return rsl;
    }

    @Override
    public Optional<MenuItemInfo> select(String itemName) {
        Optional<MenuItemInfo> rsl = Optional.empty();
        Iterator<MenuItemInfo> iterator = this.iterator();
        while (iterator.hasNext()) {
            MenuItemInfo itemInfo = iterator.next();
            if (itemName.equals(itemInfo.getName())) {
                rsl = Optional.of(itemInfo);
                break;
            }
        }
        return rsl;
    }

    @Override
    public Iterator<MenuItemInfo> iterator() {
        return new Iterator<>() {
            private Iterator<ItemInfo> iterator = new DFSIterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public MenuItemInfo next() {
                List<String> children = new ArrayList<>();
                ItemInfo item = iterator.next();
                for (MenuItem child : item.menuItem.getChildren()) {
                    children.add(child.getName());
                }
                return new MenuItemInfo(item.menuItem.getName(), children, item.menuItem.getActionDelegate(), item.number);
            }
        };
    }

    private Optional<ItemInfo> findItem(String name) {
        Optional<ItemInfo> rsl = Optional.empty();
        Iterator<ItemInfo> iterator = this.new DFSIterator();
        while (iterator.hasNext()) {
            ItemInfo item = iterator.next();
            if (item.menuItem.getName().equals(name)) {
                rsl = Optional.of(item);
                break;
            }
        }
        return rsl;
    }

    private static class SimpleMenuItem implements MenuItem {

        private String name;
        private List<MenuItem> children = new ArrayList<>();
        private ActionDelegate actionDelegate;

        public SimpleMenuItem(String name, ActionDelegate actionDelegate) {
            this.name = name;
            this.actionDelegate = actionDelegate;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<MenuItem> getChildren() {
            return children;
        }

        @Override
        public ActionDelegate getActionDelegate() {
            return actionDelegate;
        }

        @Override
        public String toString() {
            return "SimpleMenuItem{"
                    + "name='" + name + '\''
                    + ", children=" + children
                    + ", actionDelegate=" + actionDelegate
                    + '}';
        }
    }

    private class DFSIterator implements Iterator<ItemInfo> {

        private Deque<MenuItem> stack = new LinkedList<>();

        private Deque<String> numbers = new LinkedList<>();

        DFSIterator() {
            int number = 1;
            for (MenuItem item : rootElements) {
                stack.addLast(item);
                numbers.addLast(String.valueOf(number++).concat("."));
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public ItemInfo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MenuItem current = stack.removeFirst();
            String lastNumber = numbers.removeFirst();
            List<MenuItem> children = current.getChildren();
            int currentNumber = children.size();
            for (var i = children.listIterator(children.size()); i.hasPrevious();) {
                stack.addFirst(i.previous());
                numbers.addFirst(lastNumber.concat(String.valueOf(currentNumber--)).concat("."));
            }
            return new ItemInfo(current, lastNumber);
        }
    }

    private class ItemInfo {

        private MenuItem menuItem;
        private String number;

        public ItemInfo(MenuItem menuItem, String number) {
            this.menuItem = menuItem;
            this.number = number;
        }

        @Override
        public String toString() {
            return "ItemInfo{"
                    + "menuItem=" + menuItem
                    + ", number='" + number + '\''
                    + '}';
        }
    }
}