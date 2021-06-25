package com.mygdx.game.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class game extends ApplicationAdapter {

    private SpriteBatch batch;
    private Random random;
    BitmapFont textoPontuacao;
    private boolean passouCano = false;
    private ShapeRenderer shadeRenderer;
    private Circle circuloPassaro;
    private Rectangle retanguloCanoCima;
    private Rectangle retanguloCanoBaixo;
    private float variacao = 0;
    private float posicaoInicialVerticalPassaro = 0;
    private float posicaoCanoVertical;
    private float posicaoCanoHorizontal;
    private float espacoEntreCanos;

    private float larguraDispositivo;//variaveis para a largura da tela
    private float alturaDispositivo; //variaveis para a altura da tela
    private Texture fundo; // textura do fundo
    private Texture[] passaros; // texturas para o pássaro
    private Texture canoBaixo;//textura para os canos de baixo
    private Texture canoTopo;//textura para os canos de cima
    private int gravidade = 0;//variaveis para a movimentação por base da gravidade
    private int pontos = 0; //variaveis dos pontos


    @Override
    public void create() {

        inicializaTexturas();
        inicializaObjetos();
    }




    @Override
    public void render() {

        verificarEstadoJogo();
        validarPontos();
        desenharTexturas();
        detectarColisao();
    }

    private void detectarColisao() {

        circuloPassaro.set(50 + passaros[0].getWidth() / 2, posicaoInicialVerticalPassaro + passaros[0].getHeight() / 2, passaros[0].getWidth() / 2);

        retanguloCanoCima.set(posicaoCanoHorizontal, alturaDispositivo / 2 - canoTopo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical, canoTopo.getWidth(), canoTopo.getHeight());
        retanguloCanoBaixo.set(posicaoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical, canoBaixo.getWidth(), canoBaixo.getHeight());
        boolean colisaoCanoCima = Intersector.overlaps(circuloPassaro, retanguloCanoCima);
        boolean colisaoCanobaixo = Intersector.overlaps(circuloPassaro, retanguloCanoBaixo);

        if( colisaoCanobaixo || colisaoCanoCima){

        }
    }

    private void desenharTexturas() {

        batch.begin();
        //criando o background com o tamanho da tela
        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
        //criando e definindo que o passáro está está no centro da tela
        batch.draw(passaros[(int) variacao], 50, posicaoInicialVerticalPassaro);
        //parte para a criação dos canos
        batch.draw(canoBaixo, posicaoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical);
        batch.draw(canoTopo, posicaoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanoVertical);
        textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo / 2, alturaDispositivo - 100);
        batch.end();
    }

    private void validarPontos() {

        //verificar se o pássaro passou pelo cano
        if(posicaoCanoHorizontal < 50 - passaros[0].getWidth()){
            if(!passouCano){
                pontos++;
                passouCano = true;
            }
        }
    }

    private void verificarEstadoJogo() {

        posicaoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;
        if(posicaoCanoHorizontal <- canoBaixo.getWidth()){
            posicaoCanoHorizontal = larguraDispositivo;
            posicaoCanoVertical = random.nextInt(400) - 200;
            passouCano = false;
        }

        boolean toqueTela = Gdx.input.justTouched();

        //para funcionar que o pássaro pule
        if(Gdx.input.justTouched()){
            gravidade = -25;
        }

        if(posicaoInicialVerticalPassaro > 0 || toqueTela)
            posicaoInicialVerticalPassaro -= gravidade;

        //criando a animação do pássaro voando
        variacao += Gdx.graphics.getDeltaTime() * 10;


        if(variacao > 3)
            variacao = 0;

        //Adicionando a gravidade
        gravidade++;
    }

    private void inicializaObjetos() {

        batch = new SpriteBatch();
        random = new Random();
        //adicionando o tamanho da tela
        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();

        posicaoInicialVerticalPassaro = alturaDispositivo / 2;
        posicaoCanoHorizontal = larguraDispositivo;
        espacoEntreCanos = 350;

        textoPontuacao = new BitmapFont();
        textoPontuacao.setColor(Color.WHITE);
        textoPontuacao.getData().setScale(10);
    }

    private void inicializaTexturas() {

        passaros = new Texture[3];
        //adicionando a textura do pássaro
        passaros[0] = new Texture("passaro1.png");
        passaros[1] = new Texture("passaro2.png");
        passaros[2] = new Texture("passaro3.png");
        //definindo o fundo com a textura que esta na pasta assets
        fundo = new Texture("fundo.png");
        canoBaixo = new Texture("cano_baixo_maior.png");
        canoTopo = new Texture("cano_topo_maior.png");
    }

    @Override
    public void dispose() {

    }
}
