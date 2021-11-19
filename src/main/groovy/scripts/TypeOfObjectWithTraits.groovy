package scripts

import com.softwood.TestEntity
import com.softwood.TestEntityWithTrait
import com.softwood.datastore.entity.AnyTrait

TestEntityWithTrait tewt = new TestEntityWithTrait(name:"with trait")
println tewt.hello()

//def res = TestEntity.withTraits(AnyTrait)
TestEntity te =  new TestEntity (name:"without trait")
def tedyntrait = te.withTraits(AnyTrait)  //returned as proxy with all methods and attributes

println tedyntrait.hello()
println tedyntrait.dump()