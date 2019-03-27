/**
 * Tämä paketti toteuttaa yksinkertaisen shakkipelin OOP kurssille (Turun avoin yliopisto).
 *
 * Peli tarjoaa tekstipohjaisen käyttöliittymän käyttäjälleen. Käyttäjä voi joko jatkaa vanhaa
 * peliä, tai aloittaa uuden pelin.
 *
 * Kun käyttäjä on valinnut uuden pelin hän voi vielä valita pelaako hän tavallista shakkia vai
 * pelaako hän transcendental shakkia.
 *
 * Pelissä ei ole toteutettu juurikaan pelimekaanisia testejä kuten shakki, matti, lopputilannetta,
 * eikä tällä hetkellä tornituskaan ole mahdollinen. Mutta muuten peliä voi ihan pelata.
 *
 * Siirtoja annetaan SAN-muodossa, eli joku seuraavista on sallittu muoto:
 * <ul>
 *     <li>e4, tätä voi käyttää vain sotilasta siirrettäessä</li>
 *     <li>dxe4 tai de4, sotilas, joka syö e4:lla olevaa nappulaa</li>
 *     <li>Nc3, upseeri ja yksiselitteinen siirto ruudulle c3</li>
 *     <li>Nbc3, upseeri, ja annetaan lähtösaraketta, jos on useampi siirto mahdollinen eri sarakkeilta samalle ruudulle</li>
 *     <li>Nb1c3, upseeri, ja koko lähtöruutu, jos on useampi siirto mahdollinen samalta sarakkeeltakin</li>
 * </ul>
 * @version 1.0
 * 
 * @author Joona Ruohola
 * @author Kimmo Hildén
 * @author Nathan Letwory
 */
package net.jokinagames;