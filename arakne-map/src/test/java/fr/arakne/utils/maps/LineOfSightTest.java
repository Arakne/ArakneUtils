/*
 * This file is part of ArakneUtils.
 *
 * ArakneUtils is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ArakneUtils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ArakneUtils.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2017-2020 Vincent Quatrevieux
 */

package fr.arakne.utils.maps;

import fr.arakne.utils.maps._test.MyDofusCell;
import fr.arakne.utils.maps._test.MyDofusMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineOfSightTest {
    private LineOfSight<MyDofusCell> lineOfSight;
    private MyDofusMap map;

    @BeforeEach
    public void setUp() throws Exception {
        map = MyDofusMap.parse("HhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaa6GHhaaeaaaaaHhaaeaaaaaHhaae6HaaaHhaae60aaaHhaaeaaaaaHhaae6HaaaHhaaeaaaaaGhaaeaaa7oHhaae6HiaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaa6SHhgSe6HaaaHhaaeaaa6IHhGaeaaaaaHhGaeaaaaaHhqaeaaaqgHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7iHhGaeaaaaaHhGaeaaa6IHhMSeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhWaeaaaaaHhGaeJgaaaHhGaeaaaaaGhaaeaaa7hHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaa6THhGaeaaaaaHhGaeaaaaaHhMSe62aaaHhGaeaaaaaHhGaeaaaaaHhGaeaaa6IHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhqaeaaaqgGhaaeaaa7AHhGaeaaaaaHhGaeaaaaaHhaae6Ha7eHhGaeaaaaaHhGaeaaaaaHhGaeaaa6IHhWaeaaaaaHhGaeaaaaaGhaaeaaa7gHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeJgaaaHhGaeaaaaaGhaaeaaa7jHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhWaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGae8uaaaGhaaeaaa7jHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGae8uaaaHhWae60aaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhqaeaaaqgHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeJgaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7iHhGaeJgaaaHhaaeaaaaaHhaaeJgaaaHhGae6HaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhWaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaa6IHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaa6IGhaaeaaa7hGhaaeaaa7iHhGaeaaaaaHhGaeaaaaaHhGaeJgaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7lGhaae8sa7gHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaGhaaeaaa7gGhaaeaaa7kHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhWae62aaaGhaaeaaa7kGhaaeaaa7hHhGaeaaaaaHhGaeaaaaaGhaaeaaa7lHhaaeaaaaaGhaaeaaa7nHhGaeaaaaaGhaaeaaa7lGhaaeaaa7jHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7hHhGaeaaaaaGhaaeaaa7mHhGaeaaaaaGhaaeJga7hHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhMTgJgaaaHhGaeaaaaaHhGaeaaa6IHhGaeaaaaaHhGaeaaaaaHhGae8saaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhMSeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaae6HaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7jHhGaeaaa6IHhGaeaaaaaHhaaeaaaaaHhaaeaaa6IHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7gHhGaeaaaaaHhGaeaaaaaHhaaeaaa6GHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaGhaaeaaa7kHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaa6GHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhgTeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaa7dHhaaeaaaaaHhaaeaaa6WHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaGhaaeaaa7yHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaa6XHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhMVgaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaa");
        lineOfSight = new LineOfSight<>(map);
    }

    @Test
    void between() {
        assertTrue(lineOfSight.between(map.get(177), map.get(210)));
        assertTrue(lineOfSight.between(map.get(210), map.get(177)));
        assertFalse(lineOfSight.between(map.get(177), map.get(146)));
        assertFalse(lineOfSight.between(map.get(146), map.get(177)));
        assertTrue(lineOfSight.between(map.get(241), map.get(242)));
        assertTrue(lineOfSight.between(map.get(242), map.get(241)));
        assertFalse(lineOfSight.between(map.get(241), map.get(243)));
        assertTrue(lineOfSight.between(map.get(137), map.get(112)));
    }

    @Test
    void betweenSameX() {
        assertTrue(lineOfSight.between(map.get(156), map.get(226)));
        assertFalse(lineOfSight.between(map.get(127), map.get(169)));
    }

    @Test
    void betweenSameCell() {
        assertTrue(lineOfSight.between(map.get(242), map.get(242)));
        assertTrue(lineOfSight.between(map.get(177), map.get(177)));
    }

    @Test
    void dump() {
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 13, 14, 16, 17, 18, 19, 20, 21, 22, 23, 24, 27, 28, 31, 32, 33, 34, 35, 36, 37, 38, 40, 41, 42, 47, 48, 49, 50, 51, 52, 54, 55, 56, 58, 62, 63, 64, 65, 66, 67, 68, 69, 73, 74, 76, 77, 78, 79, 80, 81, 82, 83, 84, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 120, 121, 122, 123, 124, 125, 134, 135, 136, 137, 138, 139, 140, 149, 150, 151, 152, 153, 154, 163, 164, 165, 166, 167, 168, 178, 179, 180, 181, 182, 183, 192, 193, 194, 195, 196, 197, 198, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 392, 393, 394, 395, 396, 397, 398, 399, 400, 401, 402, 403, 404, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 421, 422, 423, 424, 425, 426, 427, 428, 429, 430, 431, 432, 433, 434, 435, 436, 437, 438, 439, 440, 441, 442, 443, 444, 445, 446, 447, 448, 450, 451, 452, 453, 454, 455, 456, 457, 458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476, 477, 478), dumpLoS(123));
        assertEquals(Arrays.asList(4, 5, 6, 7, 8, 18, 19, 20, 21, 22, 23, 33, 34, 35, 36, 37, 48, 49, 50, 51, 52, 62, 63, 64, 65, 66, 67, 76, 77, 78, 79, 80, 81, 91, 92, 93, 94, 95, 106, 107, 108, 109, 110, 113, 120, 121, 122, 123, 124, 135, 136, 137, 138, 139, 149, 150, 151, 152, 153, 154, 155, 156, 159, 160, 162, 163, 164, 165, 166, 167, 168, 169, 170, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426, 427, 428, 429, 430, 431, 432, 433, 434, 435, 436, 437, 438, 439, 440, 441, 442, 443, 444, 445, 446, 447, 448, 449, 450, 451, 452, 453, 454, 455, 456, 457, 458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476, 477, 478), dumpLoS(384));
        assertEquals(Arrays.asList(162, 163, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426, 427, 428, 429, 430, 431, 432, 433, 434, 435, 436, 437, 438, 439, 440, 441, 442, 443, 444, 445, 446, 447, 448, 449, 450, 451, 452, 453, 454, 455, 456, 457, 458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476, 477, 478), dumpLoS(177));
    }

    private List<Integer> dumpLoS(int cell) {
        List<Integer> cells = new ArrayList<>();

        MyDofusCell source = map.get(cell);

        for (int i = 0; i < map.size(); ++i) {
            if (lineOfSight.between(source, map.get(i))) {
                cells.add(i);
            }
        }

        return cells;
    }
}