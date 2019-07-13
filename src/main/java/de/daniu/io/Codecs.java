package de.daniu.io;

import de.daniu.domain.Cube;
import de.daniu.domain.CubeColor;
import de.daniu.domain.CubeFace;
import de.daniu.domain.Faces;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Codecs {
    public enum ColorCodec implements Codec<CubeColor> {
        INSTANCE;

        public String encode(CubeColor cubeColor) {
            return EncodedColor.valueOf(cubeColor.name()).getEncoding();
        }

        public CubeColor decode(String s) {
            return CubeColor.valueOf(EncodedColor.fromEncoding(s).name());
        }
    }

    public enum CubeFaceCodec implements Codec<CubeFace> {
        INSTANCE;

        @Override
        public String encode(CubeFace toEncode) {
            return toEncode.colors().map(ColorCodec.INSTANCE::encode).collect(Collectors.joining());
        }

        @Override
        public CubeFace decode(String encoded) {
            CubeFace result = CubeFace.filled(CubeColor.WHITE);
            for (int i = 0; i < encoded.length(); i++) {
                result.setColor(i, ColorCodec.INSTANCE.decode(encoded.substring(i, i+1)));
            }
            return result;
        }
    }

    public enum CubeCodec implements Codec<Cube> {
        INSTANCE;

        /*
        uuuuuuuuu rrrrrrrrr fffffffff ddddddddd lllllllll bbbbbbbbb
         */
        static final List<Faces> FACE_ORDER = List.of(
                Faces.UP, Faces.RIGHT, Faces.FRONT, Faces.DOWN, Faces.LEFT, Faces.BACK);

        @Override
        public String encode(Cube toEncode) {
            return FACE_ORDER.stream()
                    .map(toEncode::getFace)
                    .map(CubeFaceCodec.INSTANCE::encode)
                    .collect(Collectors.joining());
        }

        @Override
        public Cube decode(String encoded) {
            int facelength = encoded.length() / Faces.values().length;
            Cube result = new Cube();
            for (int i = 0, currentStartIndex = 0;
                 currentStartIndex < encoded.length();
                 i++, currentStartIndex += facelength) {
                String encoding = encoded.substring(currentStartIndex, currentStartIndex + facelength);
                CubeFace decoded = CubeFaceCodec.INSTANCE.decode(encoding);
                result.getFace(FACE_ORDER.get(i)).copyFrom(decoded);
            }
            return result;
        }
    }

    public interface Codec<T> {
        String encode(T toEncode);

        T decode(String encoded);
    }
}
