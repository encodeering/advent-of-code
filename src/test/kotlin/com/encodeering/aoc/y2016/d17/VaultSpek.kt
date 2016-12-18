package com.encodeering.aoc.y2016.d17

import com.winterbe.expekt.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.xdescribe
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * @author clausen - encodeering@gmail.com
 */
@RunWith (JUnitPlatform::class)
class VaultSpek : Spek({

    describe ("Vault") {

        xdescribe ("#1") {

            it ("first example") {
                val     vault = Vault (4, 4)

                expect (vault.path (0 to 0, 3 to 3, "ihgpwlah")!!).to.equal ("DDRRRD")
                expect (vault.path (0 to 0, 3 to 3, "kglvqrro")!!).to.equal ("DDUDRLRRUDRD")
                expect (vault.path (0 to 0, 3 to 3, "ulqzkmiv")!!).to.equal ("DRURDRUDDLLDLUURRDULRLDUUDDDRR")
            }

        }

        describe ("#2") {

            it ("first example") {
                val     vault = Vault (4, 4)

                expect (vault.longest (0 to 0, 3 to 3, "ihgpwlah")?.length).to.equal (370)
                expect (vault.longest (0 to 0, 3 to 3, "kglvqrro")?.length).to.equal (492)
                expect (vault.longest (0 to 0, 3 to 3, "ulqzkmiv")?.length).to.equal (830)
            }

        }

    }

})