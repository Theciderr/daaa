package com.auction.util;

public final class Pagination {

    private Pagination() {
    }

    public static int sanitizePage(String pageParam) {
        try {
            int page = Integer.parseInt(pageParam);
            return Math.max(page, 1);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    public static int sanitizeSize(String sizeParam, int defaultSize, int maxSize) {
        try {
            int size = Integer.parseInt(sizeParam);
            if (size <= 0) {
                return defaultSize;
            }
            return Math.min(size, maxSize);
        } catch (NumberFormatException e) {
            return defaultSize;
        }
    }

    public static int offset(int page, int size) {
        return (page - 1) * size;
    }
}


