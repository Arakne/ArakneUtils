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

package fr.arakne.utils.maps.serializer;

import fr.arakne.utils.encoding.Key;
import fr.arakne.utils.maps.constant.CellMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptedMapDataSerializerTest {
    private EncryptedMapDataSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new EncryptedMapDataSerializer(
            Key.parse("682a5a717d49457e73274e3b3023452652224870524b735e6260457e377a4136216f7b5a7b332c55426c7b2776207136333f384333676577377828273860497a36214973525b606b6d3e7c4173716a713c6b232477664f3a6d2f79664f325f655b503e3a6f2c34202330272c4824635349657c2d554a31466a3f7e78667e485d527a203f37495d27664b5333207268452f2532426b74447e3a41215a386a6a5b70223f2d3078335a204543292d496c6366287637525723743f3e4c7155726e262f5f48703b294d4b537b544a4b3f4f7150512670323b6b43295a2e762129393254423944752e74636a6671693a235d34253235677a765841")
        );
    }

    @Test
    void deserialize() {
        String encryptedData = "784b244737432911332a3b360301201f561b205769071a3b1e524d34230d334f17411457525e0e047b0f0416521949465901011257402c12333a010a25561d20164722105d0a6b4c16072a0c5d4e1807075a3e043e315f5b0e4d7c484251421a00450232010d1d4c302b50270b5e3910071f2d3c331b17507f213c46037d1b5a4113202d4e4a0e15251f5b206932590b0f3a11435e4c7810743b452422484c28240b074913563336152777562b22304426474e3e00185a48282a321a62030357081035304711535a232b6e3b4b17404858531c2a4825104f15021b0139017d42385544061b1710292f4b3f101c28241f3b4f095a5542244733430f18332a163f030172177f120657440e1a3b1a52643d050d1e461741477f7b577510560604165619604f59012c1b574028121a33270a085f1d20121022195d0a464516072e5b254718072a533e043a317652284d51414251464d004c24322c041d4c342b792e2d5e1b19071f293c1a12775e52283c46072a1b5b67130d0f484a0a150c167d20443b590b0b3a374a5e4c5519523b172d0b416a280902074917561a3f64155a5f2d103413264e683e2d115a482c2a1b13352b2e5e2e1031306e18755a0e22483b4f1769417e5331235825144f3c0b0b0714085b423c556d0f1b173d20094b6c253521021f16462f5a51420d4e15432d11332a123f2a08082d524c7357400e33323c524934230d1a463e483657565e592252062d1f70194d4659017f337e490e12373a010a0c5f342934100f105d0a42453f0e085b084e18072e53170d2a315b5b0e4d45476458464d2d4502327e243445122b54270b5e1f192e160f3c371b415e5628154f072a360568135f2067432c15211f5b20403b70022d3a15435e4c51197b32672426484c285a2a2e402156373642155e5f041912130b474e3e29117c412c2a361a352b7c58071917304311535a0a2261326917444858533523712c324f11020b071008724b1a5540061b17392020423b101828241f1246065377422047334329111a23343f072a221f561b095e660e1e3b1a524d3405041a46134110570455702b740600165619494670080e1b53402812333a28032a5f192012100b107403744512072e5b0c4e310e08533a043a315f5b274473414651464d29452b3b0e041915202b50272d571f19031f293c6510685770283846072a3252681a2f244a4a0a15251f7229403b5d0b0b3a114377457719563b4124224865212b020349175633366b1c785f291034130f4767370f115e482c2a321a1c220c5e2a10313047117a532c224c3b4f174048715a13235c7c004f1502220e26085f156d554406321e1f200d4b3b101c280d1634462b5a514224471a4a0f11372a123f03010d16701b2457400e1a3b335b6b34270d1a461741395e525e5d22520604167f1059465d01281b4746011b153a050a0c5f1d203b192d10590a4245160707522a4e1c072e533e041338795b0a66534142516f440f45063228041d4c1d2276270f5e1f19071f0035151b455e56283c462e231452451309244e4a231c031f5f20403b590b223337435a4c5119523b672d224848280d0251413e5f1536463e585f2d101d1a0f474a3e29115a480523321a31002c5e2e1018396111570d2322483b661e66485c53352358253d4633020f0710085b42155c62061f173920094b12193a28201f12462f5a784b024737432911332a3b362501201f561b205769072c3b1e524d34230d334f31411457525e59227b0f2216521949465901011271402c12333a562225563b2016100b105d0a6b4c30072a5b0c4e1807075a3e043e315f5b0e4d7c486451424d29450232010d3b4c302b50275c763910071f2d3c331b175770213c46032a3252171b202d684a0e15251f5b2069327f0b0f3a11435e4c7810743b450f24484c28240b214913563336421577560b1030130f474e3e0f185a48282a321a63260c572e1035695511055c232b6e3b4b17404858531c2a7e25104f15020b0739017d42385544061b171029094b3f101c28241f3b4f095a5542244733430f18332a163f0301721970122057440e1a3b4c58643d050d1e46174110577b577f22560604165619604f7f012c1b574028121a33270a085f1d20121022196b0a461245072e5b2a4718072a533e046c3b79520e4d514142511044004c24322c041d4c342b792e2d5e1b19071f293c1512415e52283c4651271b5b41130d244e4a0a1503165b20443b590b5d35384a784c5519523b412404414c2809020749415b153f42155a5f2d106218264e683e2d115a482c2a1b13132b2e5e2e1031306e18755a0e22483b4f1769417e5331235825144f3c0b2d0714085b423c556d0f3d173d20094b3b103521021f16462f5a51420d4e15432d11332a123f2508241f521b2057160733323c524934230d1a4631481057565e5922040a2d1f70194d465901281b714928123711070a5a56342934100f105d0a42453f0e085b084e18072e53170d1c315b5b0e4d55416b58604d2d45023228043445122b54270b5e1f192e1605093530475e5628154f212a36524113092467432c15211f5b20171370022d3a15435e4c51197b32672426484c280d022e403156376f50155e5f041934130b474e3e291173410a2a361a352b2a5e071917304311535a0a2261326325444858533523712c324f11020b071008724b1a5540061b17392020423b101828241f1246065377422047334329111a23343f0701241f561b095e660e1e3b1a524d340a043c4613411057525e702b740600165619494670080e1b53402812333a28032a5f192012100b107403644512072e5b0c4e310e2e533a5313315f5b274473414651464d29452b3b0e04194c342b502722573919031f293c331b685770283846072a3252681a09244a4a0a15251f7229403b5d0b0b3a114377457719563b4124224865212b020349175633366b1c785f291034130f4767370f115e482c2a321a13222a5e2a103130111a7a532c224c3b4f171760715a13235c25144f1502220e10085f423c554406321e39200d4b3b104b000d1634462b5a514224471a4a0f11372a123f03010d16701b2457400e1a3b335b6b34270d1a461741395e525e5d22520604167f1049465d01281b5740011b333a050a0c5f1d203b192d10590a4245160707522a4e1c072e533e041c385f5b0a4d554114576f440f45063228041d4c1d2276270f5e1f19071f0035331b455e56286b602e233252451309244e4a231c251f5f20403b590b223337435a4c5119523b682d044848280d0207493e5f153646155e5f2d101d1a0f474a3e29115a480523321a312b2a5e2e1018394711575a0a22483b661e40485c53352358253d4633020f0710085b421a5c44061f1739205f4112193a28201f12462f5a784b024737432911332a3b360301201f561b205769071a3b1e524d34230d334f17411457525e0e047b0f0416521949465901011271402c12333a010a25563b2016100b105d0a6b4c16072a5b0c4e1807075a3e043e315f5b0e4d7c484251424d29450232010d1d4c302b50270b5e3610211f2d3c331b415e7f211a46032a32524113202d684a0e15251f5b2069327f0b0f3a11435e4c7810523b452422484c28240b017c13563336421577562d1030130f474e3e00185a48282a321a352b0357081035304711535a232b483b4b17404858531c2a5825104f15020b0739015b42385544061b171029094b3f101c28241f3b4f2f5a5542244733430018152a163f0301241f7f120657440e1a3b1a52643d050d1e46174110577b577f22560604165619604f59012c1b574028121a33010a085f1d20441522195d0a464516072e5b254718072a533e046d077652284d51414251464d004c02322c041d4c342b792e0b5e1b19071f293c1a12415e52283c46072a1b5b41130d244e4a0a150c165b20443b590b0b3a384a5e4c5519523b41240b416a280902074917561a3f42155a5f2d103413264e4e3e2d115a482c2a1b13132b2e5e2e1031306e18535a0e22483b4f176941585331235825144f330b0b0714085b426a4d6d0f3d173d20094b3b103521021f16462f5a51420d4e15432d11332a123f2a08241f521b2057400e33321a524934230d1a463e481057565e592252062d1f56194d465901281b7e490e12373a010a0c5f342912100f105d0a42453f0e2e5b084e18072e53170d3a315b5b0e4d55416b58604d2d45023228043445342b54270b5e48202e16293c371b415e5628154f212a36524113092467432c15211f5b20403b70022d3a15435e4c51197b32672426484c280d022e401756373642155e5f041934130b474e3e291173410a2a361a352b2a5e07191d074111535a0a2261326917444858533523712c144f11020b071008724b3c5540061b17392020421d101828241f1246065377422047334329111a23343f0701241f561b095e400e1e3b1a524d340a043c4613411057525e702b740600165619494670080e1b53402812333a28030c5f192012100b107403424512072e5b0c4e310e08533a043a315f5b274473414651464d29452b3b0e04194c342b502722573919031f293c331b685756283846072a3252681a2f244a4a0a15251f7229663b5d0b0b3a114377457719563b4124224865210d020349175633366b1c5e5f291034130f47673729115e482c2a321a1c220c5e2a10313047117a530a224c3b4f174048715a35235c25144f1502220e10085f423c554406321e1f200d4b3b101c280d1634462b5a514224471a4a0f11372a123f03010d16561b2457400e1a3b335b4d34270d1a461741395e745e5d22520604167f106f465d01281b5740011b153a050a0c5f1d203b190b10590a4245160707520c4e1c072e533e0413385f5b0a4d554142516f442945063228041d4c1d2250270f5e1f19071f0035331b455e56283c462e233252451309244e4a231c251f5f20403b590b223337435a4c5119523b682d224848280d0207493e5f333646155e5f2d101d1a0f474a3e29115a480523321a312b2a5e2e1018396111575a0a22483b661e40485c53352358253d4615020f0710085b42155c44061f173920094b12191c28201f12462f5a784b244737432911332a3b360301201f561b205769071a3b1e524d34230d334f17411457525e59227b0f2216521949465901011271402c12333a010a25561d2016100b105d0a6b4c16072a5b0c4e1807075a3e043e315f5b0e4d7c484251424d29450232010d1d4c302b50270b5e3610071f2d3c331b415e7f213c46032a32524113202d4e4a0e15251f5b206932590b0f3a11435e4c7810523b452422484c28240b074913563336421577562d1030130f474e3e00187c48282a321a352b03572e1035304711535a232b483b4b17404858531c2a5825104f15020b0739015b42385544061b171029094b3f101c28241f3b4f2f5a5542244733430018332a163f0301241f7f122057440e1a3b1a52643d230d1e46174110577b575922560604165619604f59012c1b574028121a33010a085f1d20121022195d0a464516072e5b254718072a533e043a3176520e4d51414251464d004c02322c041d4c342b792e0b5e1b19071f293c1a12415e52283c46072a1b5b41130d244e4a0a150c165b20443b590b0b3a384a5e4c5519523b41240b414c280902074917561a3f42155a5f2d103413264e4e3e2d115a482c2a1b13352b2e5e2e1031306e18535a0e22483b4f176941585331235825144f3c0b0b0714085b423c556d0f1b173d20094b3b103521241f16462f5a51420d4e33432d11332a123f2a08241f521b2057400e33321a524934230d1a463e481057565e592252062d1f56194d465901281b7e492812373a010a0c5f342912100f105d0a42453f0e2e5b084e18072e53170d3a315b5b0e4d55416b58464d2d45023228043445342b54270b5e1f192e16293c371b415e5628154f072a36524113092467430a15211f5b20403b70020b3a15435e4c51197b32412426484c280d022e401756373642155e5f041934130b474e3e291173412c2a361a352b2a5e071931304311535a0a2261324f17444858533523712c144f11020b071008724b3c5540061b17392020423b101828241f1246065351422047334329111a23123f0701241f561b095e400e1e3b1a524d340a041a4613411057525e702b52060016561949467008281b53402812333a28030c5f192012100b107403424512072e5b0c4e310e2e533a043a315f5b274455414651464d29452b3b2804194c342b502722571f19031f293c331b685756283846072a3252681a09244a4a0a15251f7229403b5d0b0b3a114377455119563b4124224865210d020349175633366b1c5e5f291034130f47673729115e482c2a321a1c222a5e2a10313047117a530a224c3b4f174048715a35235c25144f1502220e10085f423c554406321e39200d4b3b101c280d1612462b5a514224471a4a2911372a123f03010d16561b2457400e1a3b335b4d34270d1a461741395e525e5d22520604167f1049465d01281b5740011b333a050a0c5f1d203b190b10590a4245160707520c4e1c072e533e0413385f5b0a4d554142516f442945063228041d4c1d2250270f5e1f19071f0035331b455e56283c462e233252451309244e4a231c251f5f20403b590b223311435a4c5119523b";

        CellData[] cells = serializer.deserialize(encryptedData);
        assertEquals(479, cells.length);
        assertEquals(CellMovement.DEFAULT, cells[230].movement());

        assertEquals(
            "HhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaa6GHhaaeaaaaaHhaaeaaaaaHhaae6HaaaHhaae60aaaHhaaeaaaaaHhaae6HaaaHhaaeaaaaaGhaaeaaa7oHhaae6HiaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaa6SHhgSe6HaaaHhaaeaaa6IHhGaeaaaaaHhGaeaaaaaHhqaeaaaqgHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7iHhGaeaaaaaHhGaeaaa6IHhMSeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhWaeaaaaaHhGaeJgaaaHhGaeaaaaaGhaaeaaa7hHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaa6THhGaeaaaaaHhGaeaaaaaHhMSe62aaaHhGaeaaaaaHhGaeaaaaaHhGaeaaa6IHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhqaeaaaqgGhaaeaaa7AHhGaeaaaaaHhGaeaaaaaHhaae6Ha7eHhGaeaaaaaHhGaeaaaaaHhGaeaaa6IHhWaeaaaaaHhGaeaaaaaGhaaeaaa7gHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeJgaaaHhGaeaaaaaGhaaeaaa7jHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhWaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGae8uaaaGhaaeaaa7jHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGae8uaaaHhWae60aaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhqaeaaaqgHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeJgaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7iHhGaeJgaaaHhaaeaaaaaHhaaeJgaaaHhGae6HaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhWaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaa6IHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaa6IGhaaeaaa7hGhaaeaaa7iHhGaeaaaaaHhGaeaaaaaHhGaeJgaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7lGhaae8sa7gHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaGhaaeaaa7gGhaaeaaa7kHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhWae62aaaGhaaeaaa7kGhaaeaaa7hHhGaeaaaaaHhGaeaaaaaGhaaeaaa7lHhaaeaaaaaGhaaeaaa7nHhGaeaaaaaGhaaeaaa7lGhaaeaaa7jHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7hHhGaeaaaaaGhaaeaaa7mHhGaeaaaaaGhaaeJga7hHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhMTgJgaaaHhGaeaaaaaHhGaeaaa6IHhGaeaaaaaHhGaeaaaaaHhGae8saaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhMSeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaae6HaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7jHhGaeaaa6IHhGaeaaaaaHhaaeaaaaaHhaaeaaa6IHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaGhaaeaaa7gHhGaeaaaaaHhGaeaaaaaHhaaeaaa6GHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaGhaaeaaa7kHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaa6GHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhgTeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaa7dHhaaeaaaaaHhaaeaaa6WHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaGhaaeaaa7yHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaa6XHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhMVgaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhGaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaaHhaaeaaaaa",
            new DefaultMapDataSerializer().serialize(cells)
        );
    }

    @Test
    void deserializeSerialize() {
        String encryptedData = "784B244737432911332A3B360301201F561B205769071A3B1E524D34230D334F17411457525E0E047B0F0416521949465901011257402C12333A010A25561D20164722105D0A6B4C16072A0C5D4E1807075A3E043E315F5B0E4D7C484251421A00450232010D1D4C302B50270B5E3910071F2D3C331B17507F213C46037D1B5A4113202D4E4A0E15251F5B206932590B0F3A11435E4C7810743B452422484C28240B074913563336152777562B22304426474E3E00185A48282A321A62030357081035304711535A232B6E3B4B17404858531C2A4825104F15021B0139017D42385544061B1710292F4B3F101C28241F3B4F095A5542244733430F18332A163F030172177F120657440E1A3B1A52643D050D1E461741477F7B577510560604165619604F59012C1B574028121A33270A085F1D20121022195D0A464516072E5B254718072A533E043A317652284D51414251464D004C24322C041D4C342B792E2D5E1B19071F293C1A12775E52283C46072A1B5B67130D0F484A0A150C167D20443B590B0B3A374A5E4C5519523B172D0B416A280902074917561A3F64155A5F2D103413264E683E2D115A482C2A1B13352B2E5E2E1031306E18755A0E22483B4F1769417E5331235825144F3C0B0B0714085B423C556D0F1B173D20094B6C253521021F16462F5A51420D4E15432D11332A123F2A08082D524C7357400E33323C524934230D1A463E483657565E592252062D1F70194D4659017F337E490E12373A010A0C5F342934100F105D0A42453F0E085B084E18072E53170D2A315B5B0E4D45476458464D2D4502327E243445122B54270B5E1F192E160F3C371B415E5628154F072A360568135F2067432C15211F5B20403B70022D3A15435E4C51197B32672426484C285A2A2E402156373642155E5F041912130B474E3E29117C412C2A361A352B7C58071917304311535A0A2261326917444858533523712C324F11020B071008724B1A5540061B17392020423B101828241F1246065377422047334329111A23343F072A221F561B095E660E1E3B1A524D3405041A46134110570455702B740600165619494670080E1B53402812333A28032A5F192012100B107403744512072E5B0C4E310E08533A043A315F5B274473414651464D29452B3B0E041915202B50272D571F19031F293C6510685770283846072A3252681A2F244A4A0A15251F7229403B5D0B0B3A114377457719563B4124224865212B020349175633366B1C785F291034130F4767370F115E482C2A321A1C220C5E2A10313047117A532C224C3B4F174048715A13235C7C004F1502220E26085F156D554406321E1F200D4B3B101C280D1634462B5A514224471A4A0F11372A123F03010D16701B2457400E1A3B335B6B34270D1A461741395E525E5D22520604167F1059465D01281B4746011B153A050A0C5F1D203B192D10590A4245160707522A4E1C072E533E041338795B0A66534142516F440F45063228041D4C1D2276270F5E1F19071F0035151B455E56283C462E231452451309244E4A231C031F5F20403B590B223337435A4C5119523B672D224848280D0251413E5F1536463E585F2D101D1A0F474A3E29115A480523321A31002C5E2E1018396111570D2322483B661E66485C53352358253D4633020F0710085B42155C62061F173920094B12193A28201F12462F5A784B024737432911332A3B362501201F561B205769072C3B1E524D34230D334F31411457525E59227B0F2216521949465901011271402C12333A562225563B2016100B105D0A6B4C30072A5B0C4E1807075A3E043E315F5B0E4D7C486451424D29450232010D3B4C302B50275C763910071F2D3C331B175770213C46032A3252171B202D684A0E15251F5B2069327F0B0F3A11435E4C7810743B450F24484C28240B214913563336421577560B1030130F474E3E0F185A48282A321A63260C572E1035695511055C232B6E3B4B17404858531C2A7E25104F15020B0739017D42385544061B171029094B3F101C28241F3B4F095A5542244733430F18332A163F0301721970122057440E1A3B4C58643D050D1E46174110577B577F22560604165619604F7F012C1B574028121A33270A085F1D20121022196B0A461245072E5B2A4718072A533E046C3B79520E4D514142511044004C24322C041D4C342B792E2D5E1B19071F293C1512415E52283C4651271B5B41130D244E4A0A1503165B20443B590B5D35384A784C5519523B412404414C2809020749415B153F42155A5F2D106218264E683E2D115A482C2A1B13132B2E5E2E1031306E18755A0E22483B4F1769417E5331235825144F3C0B2D0714085B423C556D0F3D173D20094B3B103521021F16462F5A51420D4E15432D11332A123F2508241F521B2057160733323C524934230D1A4631481057565E5922040A2D1F70194D465901281B714928123711070A5A56342934100F105D0A42453F0E085B084E18072E53170D1C315B5B0E4D55416B58604D2D45023228043445122B54270B5E1F192E1605093530475E5628154F212A36524113092467432C15211F5B20171370022D3A15435E4C51197B32672426484C280D022E403156376F50155E5F041934130B474E3E291173410A2A361A352B2A5E071917304311535A0A2261326325444858533523712C324F11020B071008724B1A5540061B17392020423B101828241F1246065377422047334329111A23343F0701241F561B095E660E1E3B1A524D340A043C4613411057525E702B740600165619494670080E1B53402812333A28032A5F192012100B107403644512072E5B0C4E310E2E533A5313315F5B274473414651464D29452B3B0E04194C342B502722573919031F293C331B685770283846072A3252681A09244A4A0A15251F7229403B5D0B0B3A114377457719563B4124224865212B020349175633366B1C785F291034130F4767370F115E482C2A321A13222A5E2A103130111A7A532C224C3B4F171760715A13235C25144F1502220E10085F423C554406321E39200D4B3B104B000D1634462B5A514224471A4A0F11372A123F03010D16701B2457400E1A3B335B6B34270D1A461741395E525E5D22520604167F1049465D01281B5740011B333A050A0C5F1D203B192D10590A4245160707522A4E1C072E533E041C385F5B0A4D554114576F440F45063228041D4C1D2276270F5E1F19071F0035331B455E56286B602E233252451309244E4A231C251F5F20403B590B223337435A4C5119523B682D044848280D0207493E5F153646155E5F2D101D1A0F474A3E29115A480523321A312B2A5E2E1018394711575A0A22483B661E40485C53352358253D4633020F0710085B421A5C44061F1739205F4112193A28201F12462F5A784B024737432911332A3B360301201F561B205769071A3B1E524D34230D334F17411457525E0E047B0F0416521949465901011271402C12333A010A25563B2016100B105D0A6B4C16072A5B0C4E1807075A3E043E315F5B0E4D7C484251424D29450232010D1D4C302B50270B5E3610211F2D3C331B415E7F211A46032A32524113202D684A0E15251F5B2069327F0B0F3A11435E4C7810523B452422484C28240B017C13563336421577562D1030130F474E3E00185A48282A321A352B0357081035304711535A232B483B4B17404858531C2A5825104F15020B0739015B42385544061B171029094B3F101C28241F3B4F2F5A5542244733430018152A163F0301241F7F120657440E1A3B1A52643D050D1E46174110577B577F22560604165619604F59012C1B574028121A33010A085F1D20441522195D0A464516072E5B254718072A533E046D077652284D51414251464D004C02322C041D4C342B792E0B5E1B19071F293C1A12415E52283C46072A1B5B41130D244E4A0A150C165B20443B590B0B3A384A5E4C5519523B41240B416A280902074917561A3F42155A5F2D103413264E4E3E2D115A482C2A1B13132B2E5E2E1031306E18535A0E22483B4F176941585331235825144F330B0B0714085B426A4D6D0F3D173D20094B3B103521021F16462F5A51420D4E15432D11332A123F2A08241F521B2057400E33321A524934230D1A463E481057565E592252062D1F56194D465901281B7E490E12373A010A0C5F342912100F105D0A42453F0E2E5B084E18072E53170D3A315B5B0E4D55416B58604D2D45023228043445342B54270B5E48202E16293C371B415E5628154F212A36524113092467432C15211F5B20403B70022D3A15435E4C51197B32672426484C280D022E401756373642155E5F041934130B474E3E291173410A2A361A352B2A5E07191D074111535A0A2261326917444858533523712C144F11020B071008724B3C5540061B17392020421D101828241F1246065377422047334329111A23343F0701241F561B095E400E1E3B1A524D340A043C4613411057525E702B740600165619494670080E1B53402812333A28030C5F192012100B107403424512072E5B0C4E310E08533A043A315F5B274473414651464D29452B3B0E04194C342B502722573919031F293C331B685756283846072A3252681A2F244A4A0A15251F7229663B5D0B0B3A114377457719563B4124224865210D020349175633366B1C5E5F291034130F47673729115E482C2A321A1C220C5E2A10313047117A530A224C3B4F174048715A35235C25144F1502220E10085F423C554406321E1F200D4B3B101C280D1634462B5A514224471A4A0F11372A123F03010D16561B2457400E1A3B335B4D34270D1A461741395E745E5D22520604167F106F465D01281B5740011B153A050A0C5F1D203B190B10590A4245160707520C4E1C072E533E0413385F5B0A4D554142516F442945063228041D4C1D2250270F5E1F19071F0035331B455E56283C462E233252451309244E4A231C251F5F20403B590B223337435A4C5119523B682D224848280D0207493E5F333646155E5F2D101D1A0F474A3E29115A480523321A312B2A5E2E1018396111575A0A22483B661E40485C53352358253D4615020F0710085B42155C44061F173920094B12191C28201F12462F5A784B244737432911332A3B360301201F561B205769071A3B1E524D34230D334F17411457525E59227B0F2216521949465901011271402C12333A010A25561D2016100B105D0A6B4C16072A5B0C4E1807075A3E043E315F5B0E4D7C484251424D29450232010D1D4C302B50270B5E3610071F2D3C331B415E7F213C46032A32524113202D4E4A0E15251F5B206932590B0F3A11435E4C7810523B452422484C28240B074913563336421577562D1030130F474E3E00187C48282A321A352B03572E1035304711535A232B483B4B17404858531C2A5825104F15020B0739015B42385544061B171029094B3F101C28241F3B4F2F5A5542244733430018332A163F0301241F7F122057440E1A3B1A52643D230D1E46174110577B575922560604165619604F59012C1B574028121A33010A085F1D20121022195D0A464516072E5B254718072A533E043A3176520E4D51414251464D004C02322C041D4C342B792E0B5E1B19071F293C1A12415E52283C46072A1B5B41130D244E4A0A150C165B20443B590B0B3A384A5E4C5519523B41240B414C280902074917561A3F42155A5F2D103413264E4E3E2D115A482C2A1B13352B2E5E2E1031306E18535A0E22483B4F176941585331235825144F3C0B0B0714085B423C556D0F1B173D20094B3B103521241F16462F5A51420D4E33432D11332A123F2A08241F521B2057400E33321A524934230D1A463E481057565E592252062D1F56194D465901281B7E492812373A010A0C5F342912100F105D0A42453F0E2E5B084E18072E53170D3A315B5B0E4D55416B58464D2D45023228043445342B54270B5E1F192E16293C371B415E5628154F072A36524113092467430A15211F5B20403B70020B3A15435E4C51197B32412426484C280D022E401756373642155E5F041934130B474E3E291173412C2A361A352B2A5E071931304311535A0A2261324F17444858533523712C144F11020B071008724B3C5540061B17392020423B101828241F1246065351422047334329111A23123F0701241F561B095E400E1E3B1A524D340A041A4613411057525E702B52060016561949467008281B53402812333A28030C5F192012100B107403424512072E5B0C4E310E2E533A043A315F5B274455414651464D29452B3B2804194C342B502722571F19031F293C331B685756283846072A3252681A09244A4A0A15251F7229403B5D0B0B3A114377455119563B4124224865210D020349175633366B1C5E5F291034130F47673729115E482C2A321A1C222A5E2A10313047117A530A224C3B4F174048715A35235C25144F1502220E10085F423C554406321E39200D4B3B101C280D1612462B5A514224471A4A2911372A123F03010D16561B2457400E1A3B335B4D34270D1A461741395E525E5D22520604167F1049465D01281B5740011B333A050A0C5F1D203B190B10590A4245160707520C4E1C072E533E0413385F5B0A4D554142516F442945063228041D4C1D2250270F5E1F19071F0035331B455E56283C462E233252451309244E4A231C251F5F20403B590B223311435A4C5119523B";

        CellData[] cells = serializer.deserialize(encryptedData);

        assertEquals(encryptedData, serializer.serialize(cells));
    }
}
