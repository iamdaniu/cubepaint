package de.daniu.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class CubeFaceTest {

    @ParameterizedTest
    @EnumSource(CubeColor.class)
    void filled(CubeColor color) {
        CubeFace face = CubeFace.filled(color);
        assertThat(face.colors().allMatch(color::equals)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8 })
    void setColor(int fieldIndex) {
        CubeFace face = CubeFace.filled(CubeColor.WHITE);
        face.setColor(fieldIndex, CubeColor.YELLOW);
        assertThat(face.getColor(fieldIndex)).isSameAs(CubeColor.YELLOW);
        IntStream.range(0, 9).filter(i -> i != fieldIndex)
                 .forEach(i -> assertThat(face.getColor(i)).isSameAs(CubeColor.WHITE));
    }

    @Test
    void equalFacesEqualAndSameHashcode() {
        CubeFace face = CubeFace.filled(CubeColor.WHITE);
        CubeFace other = CubeFace.filled(CubeColor.WHITE);
        assertThat(face).isEqualTo(other);
        assertThat(face.hashCode()).isEqualTo(other.hashCode());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8 })
    void differentFacesDontEqualAndDifferentHashcode(int fieldIndex) {
        CubeFace face = CubeFace.filled(CubeColor.WHITE);
        CubeFace other = CubeFace.filled(CubeColor.WHITE);
        other.setColor(fieldIndex, CubeColor.YELLOW);
        assertThat(face).isNotEqualTo(other);
        assertThat(face.hashCode()).isNotEqualTo(other.hashCode());
    }
}
