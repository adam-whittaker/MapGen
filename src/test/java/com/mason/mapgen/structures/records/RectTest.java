package com.mason.mapgen.structures.records;

import com.mason.libgui.utils.structures.Coord;
import com.mason.libgui.utils.structures.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectTest{

    @Test
    void buildRectFromTopLeftWidthHeight() {
        Coord topLeft = new Coord(10, 20);
        Rect r = Rect.buildRect(topLeft, 5, 7);

        assertEquals(10, r.x());
        assertEquals(20, r.y());
        assertEquals(5, r.width());
        assertEquals(7, r.height());

        // size()
        Size size = r.size();
        assertEquals(5, size.width());
        assertEquals(7, size.height());
    }

    @Test
    void buildRectFromTopLeftAndSize() {
        Coord topLeft = new Coord(3, 4);
        Size size = new Size(8, 6);

        Rect r = Rect.buildRect(topLeft, size);

        assertEquals(3, r.x());
        assertEquals(4, r.y());
        assertEquals(8, r.width());
        assertEquals(6, r.height());
    }

    @Test
    void buildRectFromTopLeftAndBottomRightInclusive() {
        Coord topLeft = new Coord(2, 3);
        Coord bottomRight = new Coord(5, 7); // inclusive

        Rect r = Rect.buildRect(topLeft, bottomRight);

        // Expected width/height for inclusive corners:
        // width  = 5 - 2 + 1 = 4
        // height = 7 - 3 + 1 = 5
        assertEquals(2, r.x());
        assertEquals(3, r.y());
        assertEquals(4, r.width());
        assertEquals(5, r.height());

        // bottomRight() should give back the same bottom-right coord
        assertEquals(bottomRight, r.bottomRight());
    }

    @Test
    void withinBoundsUsesRectAsHalfOpenRegion() {
        Rect r = Rect.buildRect(new Coord(10, 20), 4, 3);
        // This should represent x ∈ [10,14), y ∈ [20,23)

        // Corners in-bounds
        assertTrue(r.withinBounds(new Coord(10, 20)));
        assertTrue(r.withinBounds(new Coord(13, 22)));

        // Edge just outside on each side
        assertFalse(r.withinBounds(new Coord(9, 20)));   // left of rect
        assertFalse(r.withinBounds(new Coord(10, 19)));  // above rect
        assertFalse(r.withinBounds(new Coord(14, 20)));  // right edge: x == x+width
        assertFalse(r.withinBounds(new Coord(10, 23)));  // bottom edge: y == y+height
    }

    @Test
    void withinInteriorIsStrictlyInsideNoBorders() {
        Rect r = Rect.buildRect(new Coord(0, 0), 5, 5);
        // x,y ∈ [0,5), interior x,y ∈ (0,4)

        // centre is interior
        assertTrue(r.withinInterior(new Coord(2, 2)));

        // borders are not interior
        assertFalse(r.withinInterior(new Coord(0, 0))); // top-left
        assertFalse(r.withinInterior(new Coord(4, 4))); // bottom-right edge (x==4 or y==4)
        assertFalse(r.withinInterior(new Coord(2, 0))); // top edge
        assertFalse(r.withinInterior(new Coord(0, 2))); // left edge
    }

    @Test
    void cornerAndMidpointAccessorsAreConsistent() {
        Rect r = Rect.buildRect(new Coord(10, 20), 4, 6);
        // x ∈ [10,14), y ∈ [20,26)

        // Expected inclusive corners if we treat bottom-right as x+width-1, y+height-1
        Coord expectedTopLeft = new Coord(10, 20);
        Coord expectedTopRight = new Coord(13, 20);
        Coord expectedBottomLeft = new Coord(10, 25);
        Coord expectedBottomRight = new Coord(13, 25);

        assertEquals(expectedTopLeft, r.topLeft());
        assertEquals(expectedTopRight, r.topRight());
        assertEquals(expectedBottomLeft, r.bottomLeft());
        assertEquals(expectedBottomRight, r.bottomRight());

        // Midpoints
        Coord centre = r.centre();
        assertEquals(10 + r.width() / 2, centre.x());
        assertEquals(20 + r.height() / 2, centre.y());

        Coord topMid = r.topMid();
        assertEquals(10 + r.width() / 2, topMid.x());
        assertEquals(20, topMid.y());

        Coord bottomMid = r.bottomMid();
        assertEquals(10 + r.width() / 2, bottomMid.x());
        assertEquals(25, bottomMid.y()); // y + height - 1

        Coord leftMid = r.leftMid();
        assertEquals(10, leftMid.x());
        assertEquals(20 + r.height() / 2, leftMid.y());

        Coord rightMid = r.rightMid();
        assertEquals(13, rightMid.x()); // x + width - 1
        assertEquals(20 + r.height() / 2, rightMid.y());
    }

    @Test
    void centreIsCollinearWithVerticalAndHorizontalMidpoints() {
        // Test several sizes to catch odd/even edge cases
        Rect[] rects = new Rect[] {
                Rect.buildRect(new Coord(0, 0), new Size(4, 4)),
                Rect.buildRect(new Coord(10, 20), new Size(5, 7)),
                Rect.buildRect(new Coord(-3, -2), new Size(6, 3))
        };

        for (Rect r : rects) {
            Coord centre = r.centre();
            Coord topMid = r.topMid();
            Coord bottomMid = r.bottomMid();
            Coord leftMid = r.leftMid();
            Coord rightMid = r.rightMid();

            // Vertical colinearity: same x
            assertEquals(centre.x(), topMid.x(),
                    "centre and topMid should share same x for rect " + r);
            assertEquals(centre.x(), bottomMid.x(),
                    "centre and bottomMid should share same x for rect " + r);

            // Horizontal colinearity: same y
            assertEquals(centre.y(), leftMid.y(),
                    "centre and leftMid should share same y for rect " + r);
            assertEquals(centre.y(), rightMid.y(),
                    "centre and rightMid should share same y for rect " + r);
        }
    }

    @Test
    void quadrantsAreSubsetsOfRect() {
        Rect r = Rect.buildRect(new Coord(0, 0), new Size(8, 6));

        Rect tl = r.topLeftQuadrant();
        Rect tr = r.topRightQuadrant();
        Rect bl = r.bottomLeftQuadrant();
        Rect br = r.bottomRightQuadrant();

        for (Coord c : coordsInRect(tl)) {
            assertTrue(r.withinBounds(c),
                    "Top-left quadrant produced coord outside parent rect: " + c);
        }
        for (Coord c : coordsInRect(tr)) {
            assertTrue(r.withinBounds(c),
                    "Top-right quadrant produced coord outside parent rect: " + c);
        }
        for (Coord c : coordsInRect(bl)) {
            assertTrue(r.withinBounds(c),
                    "Bottom-left quadrant produced coord outside parent rect: " + c);
        }
        for (Coord c : coordsInRect(br)) {
            assertTrue(r.withinBounds(c),
                    "Bottom-right quadrant produced coord outside parent rect: " + c);
        }
    }

    @Test
    void quadrantInteriorsDoNotOverlapForOddSizedRect() {
        Rect r = Rect.buildRect(new Coord(0, 0), new Size(7, 7));

        Rect tl = r.topLeftQuadrant();
        Rect tr = r.topRightQuadrant();
        Rect bl = r.bottomLeftQuadrant();
        Rect br = r.bottomRightQuadrant();

        Rect[] quads = {tl, tr, bl, br};

        // For every coordinate in the parent rect, count how many quadrant *interiors*
        // contain it; it should never be more than 1.
        for (Coord c : coordsInRect(r)) {
            int count = 0;
            for (Rect q : quads) {
                if (q.withinInterior(c)) {
                    count++;
                }
            }
            assertTrue(count <= 1,
                    "Quadrant interiors overlap at " + c + " in rect " + r);
        }
    }

    @Test
    void quadrantInteriorsDoNotOverlapForEvenSizedRect() {
        Rect r = Rect.buildRect(new Coord(5, 3), new Size(8, 6));

        Rect tl = r.topLeftQuadrant();
        Rect tr = r.topRightQuadrant();
        Rect bl = r.bottomLeftQuadrant();
        Rect br = r.bottomRightQuadrant();

        Rect[] quads = {tl, tr, bl, br};

        for (Coord c : coordsInRect(r)) {
            int count = 0;
            for (Rect q : quads) {
                if (q.withinInterior(c)) {
                    count++;
                }
            }
            assertTrue(count <= 1,
                    "Quadrant interiors overlap at " + c + " in rect " + r);
        }
    }

    @Test
    void eachInteriorPointBelongsToExactlyOneQuadrantForSimpleCase() {
        // A simple square case where we expect a clean 2x2 tiling of quadrants
        Rect r = Rect.buildRect(new Coord(0, 0), new Size(4, 4));

        Rect[] quads = r.quadrants();

        for (Coord c : coordsInRect(r)) {

            int count = 0;
            for (Rect q : quads) {
                if (q.withinBounds(c)) {
                    count++;
                }
            }
            assertEquals(1, count,
                    "Interior coordinate " + c + " should belong to exactly one quadrant");
        }
    }

    // --- helper ---

    /** Iterate all integer coords within rect bounds using withinBounds. */
    private Iterable<Coord> coordsInRect(Rect r) {
        Size s = r.size();
        Coord topLeft = r.topLeft();
        int startX = topLeft.x();
        int startY = topLeft.y();
        int width = s.width();
        int height = s.height();

        return () -> new java.util.Iterator<>() {
            int x = 0, y = 0;

            @Override
            public boolean hasNext() {
                return y < height;
            }

            @Override
            public Coord next() {
                Coord c = new Coord(startX + x, startY + y);
                x++;
                if (x == width) {
                    x = 0;
                    y++;
                }
                return c;
            }
        };
    }

}