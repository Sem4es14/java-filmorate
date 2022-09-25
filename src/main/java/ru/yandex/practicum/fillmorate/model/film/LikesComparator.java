package ru.yandex.practicum.fillmorate.model.film;

import java.util.Comparator;

public class LikesComparator implements Comparator<Film> {
    @Override
    public int compare(Film o1 , Film o2) {
        if (o1.getLikes().size() == o2.getLikes().size()) {
            return -1;
        }
        return o2.getLikes().size() - o1.getLikes().size();
    }
}
