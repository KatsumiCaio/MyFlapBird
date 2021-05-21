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
    private float larguraDispositivo;//variavel para a largura da tela
    private float alturaDispositivo;//variavel para a altura da tela
    private Texture fundo;
    private Texture[] passaros;//variavel do passaro e para a animação
    private Texture canoBaixo;
    private Texture canoTopo;
    private int gravidade = 0;//variavel da gravidade para a movimentação
    private int pontos = 0;//variavel do score


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
        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);//definindo a largura e a altura da tela com o Background
        batch.draw(passaros[(int) variacao], 50, posicaoInicialVerticalPassaro);//Definindo que o passáro ficara no meio da tela
        batch.draw(canoBaixo, posicaoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical);//colocando os canos de baixo
        batch.draw(canoTopo, posicaoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanoVertical);//colocando os canos de cima
        textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo / 2, alturaDispositivo - 100);//fazendo a pontuação aparecer na tela
        batch.end();
    }

    private void validarPontos() {


        if(posicaoCanoHorizontal < 50 - passaros[0].getWidth()){
            if(!passouCano){// verificando se o passáro passou pelo cano
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


        if(Gdx.input.justTouched()){//fazendo o passaro voar ao clicar na tel
            gravidade = -25;
        }

        if(posicaoInicialVerticalPassaro > 0 || toqueTela)
            posicaoInicialVerticalPassaro -= gravidade;


        variacao += Gdx.graphics.getDeltaTime() * 10;//criando a animação do passáro voando


        if(variacao > 3)
            variacao = 0;


        gravidade++;
    }

    private void inicializaObjetos() {

        batch = new SpriteBatch();
        random = new Random();

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

        passaros = new Texture[3];//colocando as texturas do passáro
        passaros[0] = new Texture("passaro1.png");
        passaros[1] = new Texture("passaro2.png");
        passaros[2] = new Texture("passaro3.png");
        fundo = new Texture("fundo.png");//colocando a textura do background
        canoBaixo = new Texture("cano_baixo_maior.png");//colocado a textura do cano de baixo
        canoTopo = new Texture("cano_topo_maior.png");//colocado a textura do cano de cima
    }

    @Override
    public void dispose() {

    }
}
