/*
 * Copyright (c) 2010 - 2015 Novus Partners, Inc. (http://www.novus.com)
 * Copyright (c) 2015 - 2016 Rose Toomey (https://github.com/rktoomey) and other individual contributors where noted
 *
 * Module:        salat-core
 * Class:         CharacterSpec.scala
 * Last modified: 2016-07-10 23:49:08 EDT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *           Project:  http://github.com/salat/salat
 *              Wiki:  http://github.com/salat/salat/wiki
 *             Slack:  https://scala-salat.slack.com
 *      Mailing list:  http://groups.google.com/group/scala-salat
 *     StackOverflow:  http://stackoverflow.com/questions/tagged/salat
 *
 */
package com.novus.salat.test

import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.test.global._
import com.novus.salat.test.model._

class CharacterSpec extends SalatSpec {

  "A grater" should {
    "support characters" in {
      val k = Kate(axe = 'A', struckWith = 'S')
      val dbo: MongoDBObject = grater[Kate].asDBObject(k)
      //      println(MapPrettyPrinter(dbo))
      dbo must havePair("_typeHint" -> "com.novus.salat.test.model.Kate")
      dbo must havePair("axe" -> "A")
      dbo must havePair("struckWith" -> "S")

      val coll = MongoConnection()(SalatSpecDb)("scala_character_test_1")
      val wr = coll.insert(dbo)
      //      println("WR: %s".format(wr))

      val k_* = grater[Kate].asObject(coll.findOne().get)
      k_* must_== k
    }
  }

}
