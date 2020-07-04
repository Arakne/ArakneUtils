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

package fr.arakne.utils.encoding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChecksumTest {
    @Test
    void integer() {
        assertEquals(13, Checksum.integer("Hello World !"));
        assertEquals(11, Checksum.integer("Hello World ?"));
        assertEquals(0, Checksum.integer(""));
        assertEquals(11, Checksum.integer("\n" +
            "\n" +
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In aliquam placerat est vitae aliquam. Nullam convallis id risus fermentum auctor. Morbi mollis auctor augue id bibendum. Nullam porta iaculis ante, a vulputate ante ullamcorper a. Nulla ut orci vitae quam finibus imperdiet. Nam quam ex, elementum vel sapien non, feugiat facilisis lectus. Aliquam erat volutpat. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aliquam condimentum euismod mi eu egestas. Sed lacinia vel ipsum quis blandit. Sed sapien turpis, sollicitudin nec posuere ac, elementum eu libero. Sed in nisi sem. Sed iaculis, tellus non viverra sollicitudin, augue nibh tincidunt ipsum, sit amet blandit nisl ante vel nulla. Nam sollicitudin tellus vel risus placerat fermentum. Donec mollis eros elit, sed ornare ante vestibulum nec.\n" +
            "\n" +
            "Proin rutrum est justo, in tincidunt orci venenatis eleifend. Vivamus magna orci, condimentum quis lacinia vitae, condimentum non felis. Sed consequat, tellus in commodo ullamcorper, urna elit euismod nisi, porttitor feugiat justo ipsum a mauris. Nulla tempus, arcu a pretium egestas, lacus enim cursus metus, quis tristique magna purus eget diam. Praesent nec mi eget quam congue viverra vitae vitae lorem. Etiam at felis neque. Sed pulvinar lorem et maximus dignissim. Vivamus ullamcorper eget orci nec suscipit. Vivamus feugiat magna vel arcu tempor, eu consectetur ipsum pretium.\n" +
            "\n" +
            "Integer tortor sapien, euismod id elementum et, euismod ut libero. Cras molestie turpis a convallis vestibulum. Praesent suscipit, mauris nec ultrices commodo, purus magna venenatis nibh, eu venenatis lacus enim tincidunt velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Ut vehicula a dolor quis rutrum. Proin mollis sit amet tellus sed eleifend. Vivamus posuere ipsum ac mauris posuere, vel dignissim lectus accumsan. Vivamus ut orci eros. Morbi efficitur leo eget tristique finibus. Suspendisse id condimentum ligula. Cras bibendum massa eget magna tristique imperdiet. Donec accumsan ultricies leo, id pulvinar augue suscipit eu. Duis nec ipsum quam. Duis ullamcorper eros eget erat feugiat bibendum. Nullam id aliquam justo, id imperdiet ex.\n" +
            "\n" +
            "Aliquam vel risus in mauris eleifend consectetur. Morbi in lectus odio. Vivamus euismod justo id dignissim scelerisque. Nunc placerat nunc velit, non imperdiet sem bibendum et. Etiam vitae ante nec ex convallis pulvinar. Suspendisse non massa sed nisl sodales pretium. Praesent at ullamcorper lectus, nec sagittis augue. Proin facilisis purus purus. Ut ut egestas nulla. Cras tincidunt lectus libero, sit amet finibus erat feugiat sed. Integer molestie quam eget venenatis placerat. Sed ultrices faucibus posuere. Mauris malesuada urna at sapien tempus tempus. Praesent commodo libero arcu, sed pretium lacus facilisis eu.\n" +
            "\n" +
            "Ut pretium consectetur sem, vitae ultricies turpis volutpat in. Etiam ac felis dolor. Suspendisse potenti. Aliquam iaculis est dictum enim tincidunt, non ultricies quam viverra. Praesent eu nulla eget felis pellentesque molestie. Maecenas id leo tempor, molestie enim id, lobortis orci. Nullam imperdiet, erat vel pulvinar venenatis, diam tellus ultricies sem, porta sollicitudin massa massa non elit. Morbi porttitor pretium mauris, a pulvinar turpis venenatis nec. "));
    }

    @Test
    void hexadecimal() {
        assertEquals("D", Checksum.hexadecimal("Hello World !"));
        assertEquals("B", Checksum.hexadecimal("Hello World ?"));
        assertEquals("0", Checksum.hexadecimal(""));
        assertEquals("B", Checksum.hexadecimal("\n" +
            "\n" +
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In aliquam placerat est vitae aliquam. Nullam convallis id risus fermentum auctor. Morbi mollis auctor augue id bibendum. Nullam porta iaculis ante, a vulputate ante ullamcorper a. Nulla ut orci vitae quam finibus imperdiet. Nam quam ex, elementum vel sapien non, feugiat facilisis lectus. Aliquam erat volutpat. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aliquam condimentum euismod mi eu egestas. Sed lacinia vel ipsum quis blandit. Sed sapien turpis, sollicitudin nec posuere ac, elementum eu libero. Sed in nisi sem. Sed iaculis, tellus non viverra sollicitudin, augue nibh tincidunt ipsum, sit amet blandit nisl ante vel nulla. Nam sollicitudin tellus vel risus placerat fermentum. Donec mollis eros elit, sed ornare ante vestibulum nec.\n" +
            "\n" +
            "Proin rutrum est justo, in tincidunt orci venenatis eleifend. Vivamus magna orci, condimentum quis lacinia vitae, condimentum non felis. Sed consequat, tellus in commodo ullamcorper, urna elit euismod nisi, porttitor feugiat justo ipsum a mauris. Nulla tempus, arcu a pretium egestas, lacus enim cursus metus, quis tristique magna purus eget diam. Praesent nec mi eget quam congue viverra vitae vitae lorem. Etiam at felis neque. Sed pulvinar lorem et maximus dignissim. Vivamus ullamcorper eget orci nec suscipit. Vivamus feugiat magna vel arcu tempor, eu consectetur ipsum pretium.\n" +
            "\n" +
            "Integer tortor sapien, euismod id elementum et, euismod ut libero. Cras molestie turpis a convallis vestibulum. Praesent suscipit, mauris nec ultrices commodo, purus magna venenatis nibh, eu venenatis lacus enim tincidunt velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Ut vehicula a dolor quis rutrum. Proin mollis sit amet tellus sed eleifend. Vivamus posuere ipsum ac mauris posuere, vel dignissim lectus accumsan. Vivamus ut orci eros. Morbi efficitur leo eget tristique finibus. Suspendisse id condimentum ligula. Cras bibendum massa eget magna tristique imperdiet. Donec accumsan ultricies leo, id pulvinar augue suscipit eu. Duis nec ipsum quam. Duis ullamcorper eros eget erat feugiat bibendum. Nullam id aliquam justo, id imperdiet ex.\n" +
            "\n" +
            "Aliquam vel risus in mauris eleifend consectetur. Morbi in lectus odio. Vivamus euismod justo id dignissim scelerisque. Nunc placerat nunc velit, non imperdiet sem bibendum et. Etiam vitae ante nec ex convallis pulvinar. Suspendisse non massa sed nisl sodales pretium. Praesent at ullamcorper lectus, nec sagittis augue. Proin facilisis purus purus. Ut ut egestas nulla. Cras tincidunt lectus libero, sit amet finibus erat feugiat sed. Integer molestie quam eget venenatis placerat. Sed ultrices faucibus posuere. Mauris malesuada urna at sapien tempus tempus. Praesent commodo libero arcu, sed pretium lacus facilisis eu.\n" +
            "\n" +
            "Ut pretium consectetur sem, vitae ultricies turpis volutpat in. Etiam ac felis dolor. Suspendisse potenti. Aliquam iaculis est dictum enim tincidunt, non ultricies quam viverra. Praesent eu nulla eget felis pellentesque molestie. Maecenas id leo tempor, molestie enim id, lobortis orci. Nullam imperdiet, erat vel pulvinar venenatis, diam tellus ultricies sem, porta sollicitudin massa massa non elit. Morbi porttitor pretium mauris, a pulvinar turpis venenatis nec. "));
    }

    @Test
    void verify() {
        assertTrue(Checksum.verify("Hello World !", "D"));
        assertTrue(Checksum.verify("Hello World !", "d"));
        assertTrue(Checksum.verify("Hello World !", 13));
        assertFalse(Checksum.verify("Hello World !", "E"));
        assertFalse(Checksum.verify("Hello World !", 11));
    }
}
