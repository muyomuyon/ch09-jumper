package com.badlogic.androidgames.framework.gl;

public class Font {
    /*フォントのグリフを含むテクスチャ*/
    public final Texture texture;
    /*１つのグリフの幅と高さ*/
    public final int glyphWidth;
    public final int glyphHeight;
    /*テクスチャリージョンを格納する配列。
    * 最初の要素はコード32のASCII文字（スペース）に対応。最後の要素はコード127のASCII文字に対応。
    * http://ja.wikipedia.org/wiki/ASCII */
    public final TextureRegion[] glyphs = new TextureRegion[96];

    //コンストラクタ
    public Font(Texture texture,//テクスチャアトラス
                int offsetX, int offsetY,//テクスチャアトラス内で、ビットマップフォント領域の左上角座標。
                int glyphsPerRow,//１行あたりのグリフの数。
                int glyphWidth, int glyphHeight/*１つのグリフのサイズ*/) {
        this.texture = texture;
        this.glyphWidth = glyphWidth;
        this.glyphHeight = glyphHeight;
        int x = offsetX;
        int y = offsetY;
        for(int i = 0; i < 96; i++) {
            glyphs[i] = new TextureRegion(texture, x, y, glyphWidth, glyphHeight);
            x += glyphWidth;
            /*ビットマップフォント領域の右端に到達したら改行する*/
            if(x == offsetX + glyphsPerRow * glyphWidth) {
                x = offsetX;
                y += glyphHeight;
            }
        }
    }

    public void drawText(SpriteBatcher batcher,
                         String text,/*画面に描画する１行のテキスト*/
                         float x, float y/*テキストの描画を開始する座標*/) {
        int len = text.length();
        for(int i = 0; i < len; i++) {
            int c = text.charAt(i) - ' ';
            if(c < 0 || c > glyphs.length - 1)
                continue;

            TextureRegion glyph = glyphs[c];
            batcher.drawSprite(x, y, glyphWidth, glyphHeight, glyph);
            x += glyphWidth;
        }
    }
}
